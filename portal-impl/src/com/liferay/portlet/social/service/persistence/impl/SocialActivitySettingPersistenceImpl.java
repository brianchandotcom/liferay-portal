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

package com.liferay.portlet.social.service.persistence.impl;

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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.social.model.impl.SocialActivitySettingImpl;
import com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl;
import com.liferay.social.kernel.exception.NoSuchActivitySettingException;
import com.liferay.social.kernel.model.SocialActivitySetting;
import com.liferay.social.kernel.service.persistence.SocialActivitySettingPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the social activity setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySettingPersistenceImpl
	extends BasePersistenceImpl<SocialActivitySetting>
	implements SocialActivitySettingPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SocialActivitySettingUtil</code> to access the social activity setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SocialActivitySettingImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the social activity settings where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<SocialActivitySetting> list = null;

		if (useFinderCache) {
			list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySetting socialActivitySetting : list) {
					if (groupId != socialActivitySetting.getGroupId()) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SocialActivitySetting>)QueryUtil.list(
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByGroupId_First(
			long groupId,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByGroupId_First(
			groupId, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByGroupId_First(
		long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		List<SocialActivitySetting> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByGroupId_Last(
			long groupId,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByGroupId_Last(
		long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySetting> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting[] findByGroupId_PrevAndNext(
			long activitySettingId, long groupId,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = findByPrimaryKey(
			activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, socialActivitySetting, groupId, orderByComparator,
				true);

			array[1] = socialActivitySetting;

			array[2] = getByGroupId_PrevAndNext(
				session, socialActivitySetting, groupId, orderByComparator,
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

	protected SocialActivitySetting getByGroupId_PrevAndNext(
		Session session, SocialActivitySetting socialActivitySetting,
		long groupId,
		OrderByComparator<SocialActivitySetting> orderByComparator,
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

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySetting)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity settings where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SocialActivitySetting socialActivitySetting :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialActivitySetting);
		}
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity settings
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"socialActivitySetting.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;

	/**
	 * Returns all the social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C(
		long groupId, long classNameId) {

		return findByG_C(
			groupId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C(
		long groupId, long classNameId, int start, int end) {

		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		return findByG_C(
			groupId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C;
				finderArgs = new Object[] {groupId, classNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C;
			finderArgs = new Object[] {
				groupId, classNameId, start, end, orderByComparator
			};
		}

		List<SocialActivitySetting> list = null;

		if (useFinderCache) {
			list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySetting socialActivitySetting : list) {
					if ((groupId != socialActivitySetting.getGroupId()) ||
						(classNameId !=
							socialActivitySetting.getClassNameId())) {

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

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				list = (List<SocialActivitySetting>)QueryUtil.list(
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByG_C_First(
			long groupId, long classNameId,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByG_C_First(
			groupId, classNameId, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_C_First(
		long groupId, long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		List<SocialActivitySetting> list = findByG_C(
			groupId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByG_C_Last(
			long groupId, long classNameId,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByG_C_Last(
			groupId, classNameId, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_C_Last(
		long groupId, long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySetting> list = findByG_C(
			groupId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting[] findByG_C_PrevAndNext(
			long activitySettingId, long groupId, long classNameId,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = findByPrimaryKey(
			activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByG_C_PrevAndNext(
				session, socialActivitySetting, groupId, classNameId,
				orderByComparator, true);

			array[1] = socialActivitySetting;

			array[2] = getByG_C_PrevAndNext(
				session, socialActivitySetting, groupId, classNameId,
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

	protected SocialActivitySetting getByG_C_PrevAndNext(
		Session session, SocialActivitySetting socialActivitySetting,
		long groupId, long classNameId,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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
			query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySetting)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity settings where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (SocialActivitySetting socialActivitySetting :
				findByG_C(
					groupId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialActivitySetting);
		}
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching social activity settings
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		FinderPath finderPath = _finderPathCountByG_C;

		Object[] finderArgs = new Object[] {groupId, classNameId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"socialActivitySetting.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 =
		"socialActivitySetting.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;

	/**
	 * Returns all the social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @return the matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_A(
		long groupId, int activityType) {

		return findByG_A(
			groupId, activityType, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_A(
		long groupId, int activityType, int start, int end) {

		return findByG_A(groupId, activityType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_A(
		long groupId, int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		return findByG_A(
			groupId, activityType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_A(
		long groupId, int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_A;
				finderArgs = new Object[] {groupId, activityType};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_A;
			finderArgs = new Object[] {
				groupId, activityType, start, end, orderByComparator
			};
		}

		List<SocialActivitySetting> list = null;

		if (useFinderCache) {
			list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySetting socialActivitySetting : list) {
					if ((groupId != socialActivitySetting.getGroupId()) ||
						(activityType !=
							socialActivitySetting.getActivityType())) {

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

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVITYTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(activityType);

				list = (List<SocialActivitySetting>)QueryUtil.list(
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByG_A_First(
			long groupId, int activityType,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByG_A_First(
			groupId, activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_A_First(
		long groupId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		List<SocialActivitySetting> list = findByG_A(
			groupId, activityType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByG_A_Last(
			long groupId, int activityType,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByG_A_Last(
			groupId, activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_A_Last(
		long groupId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		int count = countByG_A(groupId, activityType);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySetting> list = findByG_A(
			groupId, activityType, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting[] findByG_A_PrevAndNext(
			long activitySettingId, long groupId, int activityType,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = findByPrimaryKey(
			activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByG_A_PrevAndNext(
				session, socialActivitySetting, groupId, activityType,
				orderByComparator, true);

			array[1] = socialActivitySetting;

			array[2] = getByG_A_PrevAndNext(
				session, socialActivitySetting, groupId, activityType,
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

	protected SocialActivitySetting getByG_A_PrevAndNext(
		Session session, SocialActivitySetting socialActivitySetting,
		long groupId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVITYTYPE_2);

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
			query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(activityType);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySetting)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity settings where groupId = &#63; and activityType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 */
	@Override
	public void removeByG_A(long groupId, int activityType) {
		for (SocialActivitySetting socialActivitySetting :
				findByG_A(
					groupId, activityType, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialActivitySetting);
		}
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @return the number of matching social activity settings
	 */
	@Override
	public int countByG_A(long groupId, int activityType) {
		FinderPath finderPath = _finderPathCountByG_A;

		Object[] finderArgs = new Object[] {groupId, activityType};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVITYTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(activityType);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 =
		"socialActivitySetting.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ACTIVITYTYPE_2 =
		"socialActivitySetting.activityType = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_A;
	private FinderPath _finderPathWithoutPaginationFindByG_C_A;
	private FinderPath _finderPathCountByG_C_A;

	/**
	 * Returns all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @return the matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C_A(
		long groupId, long classNameId, int activityType) {

		return findByG_C_A(
			groupId, classNameId, activityType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C_A(
		long groupId, long classNameId, int activityType, int start, int end) {

		return findByG_C_A(
			groupId, classNameId, activityType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C_A(
		long groupId, long classNameId, int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		return findByG_C_A(
			groupId, classNameId, activityType, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findByG_C_A(
		long groupId, long classNameId, int activityType, int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_A;
				finderArgs = new Object[] {groupId, classNameId, activityType};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_A;
			finderArgs = new Object[] {
				groupId, classNameId, activityType, start, end,
				orderByComparator
			};
		}

		List<SocialActivitySetting> list = null;

		if (useFinderCache) {
			list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySetting socialActivitySetting : list) {
					if ((groupId != socialActivitySetting.getGroupId()) ||
						(classNameId !=
							socialActivitySetting.getClassNameId()) ||
						(activityType !=
							socialActivitySetting.getActivityType())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(activityType);

				list = (List<SocialActivitySetting>)QueryUtil.list(
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByG_C_A_First(
			long groupId, long classNameId, int activityType,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByG_C_A_First(
			groupId, classNameId, activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_C_A_First(
		long groupId, long classNameId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		List<SocialActivitySetting> list = findByG_C_A(
			groupId, classNameId, activityType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByG_C_A_Last(
			long groupId, long classNameId, int activityType,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByG_C_A_Last(
			groupId, classNameId, activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append("}");

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_C_A_Last(
		long groupId, long classNameId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		int count = countByG_C_A(groupId, classNameId, activityType);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySetting> list = findByG_C_A(
			groupId, classNameId, activityType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting[] findByG_C_A_PrevAndNext(
			long activitySettingId, long groupId, long classNameId,
			int activityType,
			OrderByComparator<SocialActivitySetting> orderByComparator)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = findByPrimaryKey(
			activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByG_C_A_PrevAndNext(
				session, socialActivitySetting, groupId, classNameId,
				activityType, orderByComparator, true);

			array[1] = socialActivitySetting;

			array[2] = getByG_C_A_PrevAndNext(
				session, socialActivitySetting, groupId, classNameId,
				activityType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySetting getByG_C_A_PrevAndNext(
		Session session, SocialActivitySetting socialActivitySetting,
		long groupId, long classNameId, int activityType,
		OrderByComparator<SocialActivitySetting> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2);

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
			query.append(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(activityType);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialActivitySetting)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 */
	@Override
	public void removeByG_C_A(
		long groupId, long classNameId, int activityType) {

		for (SocialActivitySetting socialActivitySetting :
				findByG_C_A(
					groupId, classNameId, activityType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(socialActivitySetting);
		}
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @return the number of matching social activity settings
	 */
	@Override
	public int countByG_C_A(long groupId, long classNameId, int activityType) {
		FinderPath finderPath = _finderPathCountByG_C_A;

		Object[] finderArgs = new Object[] {groupId, classNameId, activityType};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(activityType);

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

	private static final String _FINDER_COLUMN_G_C_A_GROUPID_2 =
		"socialActivitySetting.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_A_CLASSNAMEID_2 =
		"socialActivitySetting.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2 =
		"socialActivitySetting.activityType = ?";

	private FinderPath _finderPathFetchByG_C_A_N;
	private FinderPath _finderPathCountByG_C_A_N;

	/**
	 * Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or throws a <code>NoSuchActivitySettingException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the matching social activity setting
	 * @throws NoSuchActivitySettingException if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting findByG_C_A_N(
			long groupId, long classNameId, int activityType, String name)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByG_C_A_N(
			groupId, classNameId, activityType, name);

		if (socialActivitySetting == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", activityType=");
			msg.append(activityType);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchActivitySettingException(msg.toString());
		}

		return socialActivitySetting;
	}

	/**
	 * Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_C_A_N(
		long groupId, long classNameId, int activityType, String name) {

		return fetchByG_C_A_N(groupId, classNameId, activityType, name, true);
	}

	/**
	 * Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 */
	@Override
	public SocialActivitySetting fetchByG_C_A_N(
		long groupId, long classNameId, int activityType, String name,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, classNameId, activityType, name
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_C_A_N, finderArgs, this);
		}

		if (result instanceof SocialActivitySetting) {
			SocialActivitySetting socialActivitySetting =
				(SocialActivitySetting)result;

			if ((groupId != socialActivitySetting.getGroupId()) ||
				(classNameId != socialActivitySetting.getClassNameId()) ||
				(activityType != socialActivitySetting.getActivityType()) ||
				!Objects.equals(name, socialActivitySetting.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_ACTIVITYTYPE_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_C_A_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_C_A_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(activityType);

				if (bindName) {
					qPos.add(name);
				}

				List<SocialActivitySetting> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_C_A_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, classNameId, activityType, name
								};
							}

							_log.warn(
								"SocialActivitySettingPersistenceImpl.fetchByG_C_A_N(long, long, int, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SocialActivitySetting socialActivitySetting = list.get(0);

					result = socialActivitySetting;

					cacheResult(socialActivitySetting);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByG_C_A_N, finderArgs);
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
			return (SocialActivitySetting)result;
		}
	}

	/**
	 * Removes the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the social activity setting that was removed
	 */
	@Override
	public SocialActivitySetting removeByG_C_A_N(
			long groupId, long classNameId, int activityType, String name)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = findByG_C_A_N(
			groupId, classNameId, activityType, name);

		return remove(socialActivitySetting);
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the number of matching social activity settings
	 */
	@Override
	public int countByG_C_A_N(
		long groupId, long classNameId, int activityType, String name) {

		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_C_A_N;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, activityType, name
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_ACTIVITYTYPE_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_C_A_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_C_A_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(activityType);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_G_C_A_N_GROUPID_2 =
		"socialActivitySetting.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_A_N_CLASSNAMEID_2 =
		"socialActivitySetting.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_A_N_ACTIVITYTYPE_2 =
		"socialActivitySetting.activityType = ? AND ";

	private static final String _FINDER_COLUMN_G_C_A_N_NAME_2 =
		"socialActivitySetting.name = ?";

	private static final String _FINDER_COLUMN_G_C_A_N_NAME_3 =
		"(socialActivitySetting.name IS NULL OR socialActivitySetting.name = '')";

	public SocialActivitySettingPersistenceImpl() {
		setModelClass(SocialActivitySetting.class);

		setModelImplClass(SocialActivitySettingImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the social activity setting in the entity cache if it is enabled.
	 *
	 * @param socialActivitySetting the social activity setting
	 */
	@Override
	public void cacheResult(SocialActivitySetting socialActivitySetting) {
		EntityCacheUtil.putResult(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			socialActivitySetting.getPrimaryKey(), socialActivitySetting);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_A_N,
			new Object[] {
				socialActivitySetting.getGroupId(),
				socialActivitySetting.getClassNameId(),
				socialActivitySetting.getActivityType(),
				socialActivitySetting.getName()
			},
			socialActivitySetting);

		socialActivitySetting.resetOriginalValues();
	}

	/**
	 * Caches the social activity settings in the entity cache if it is enabled.
	 *
	 * @param socialActivitySettings the social activity settings
	 */
	@Override
	public void cacheResult(
		List<SocialActivitySetting> socialActivitySettings) {

		for (SocialActivitySetting socialActivitySetting :
				socialActivitySettings) {

			if (EntityCacheUtil.getResult(
					SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivitySettingImpl.class,
					socialActivitySetting.getPrimaryKey()) == null) {

				cacheResult(socialActivitySetting);
			}
			else {
				socialActivitySetting.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SocialActivitySettingImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivitySetting socialActivitySetting) {
		EntityCacheUtil.removeResult(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			socialActivitySetting.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SocialActivitySettingModelImpl)socialActivitySetting, true);
	}

	@Override
	public void clearCache(List<SocialActivitySetting> socialActivitySettings) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivitySetting socialActivitySetting :
				socialActivitySettings) {

			EntityCacheUtil.removeResult(
				SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySettingImpl.class,
				socialActivitySetting.getPrimaryKey());

			clearUniqueFindersCache(
				(SocialActivitySettingModelImpl)socialActivitySetting, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SocialActivitySettingModelImpl socialActivitySettingModelImpl) {

		Object[] args = new Object[] {
			socialActivitySettingModelImpl.getGroupId(),
			socialActivitySettingModelImpl.getClassNameId(),
			socialActivitySettingModelImpl.getActivityType(),
			socialActivitySettingModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_C_A_N, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_A_N, args, socialActivitySettingModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SocialActivitySettingModelImpl socialActivitySettingModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				socialActivitySettingModelImpl.getGroupId(),
				socialActivitySettingModelImpl.getClassNameId(),
				socialActivitySettingModelImpl.getActivityType(),
				socialActivitySettingModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_A_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_C_A_N, args);
		}

		if ((socialActivitySettingModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_C_A_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				socialActivitySettingModelImpl.getOriginalGroupId(),
				socialActivitySettingModelImpl.getOriginalClassNameId(),
				socialActivitySettingModelImpl.getOriginalActivityType(),
				socialActivitySettingModelImpl.getOriginalName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_A_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_C_A_N, args);
		}
	}

	/**
	 * Creates a new social activity setting with the primary key. Does not add the social activity setting to the database.
	 *
	 * @param activitySettingId the primary key for the new social activity setting
	 * @return the new social activity setting
	 */
	@Override
	public SocialActivitySetting create(long activitySettingId) {
		SocialActivitySetting socialActivitySetting =
			new SocialActivitySettingImpl();

		socialActivitySetting.setNew(true);
		socialActivitySetting.setPrimaryKey(activitySettingId);

		socialActivitySetting.setCompanyId(CompanyThreadLocal.getCompanyId());

		return socialActivitySetting;
	}

	/**
	 * Removes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting that was removed
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting remove(long activitySettingId)
		throws NoSuchActivitySettingException {

		return remove((Serializable)activitySettingId);
	}

	/**
	 * Removes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity setting
	 * @return the social activity setting that was removed
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting remove(Serializable primaryKey)
		throws NoSuchActivitySettingException {

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting socialActivitySetting =
				(SocialActivitySetting)session.get(
					SocialActivitySettingImpl.class, primaryKey);

			if (socialActivitySetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivitySettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(socialActivitySetting);
		}
		catch (NoSuchActivitySettingException nsee) {
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
	protected SocialActivitySetting removeImpl(
		SocialActivitySetting socialActivitySetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivitySetting)) {
				socialActivitySetting = (SocialActivitySetting)session.get(
					SocialActivitySettingImpl.class,
					socialActivitySetting.getPrimaryKeyObj());
			}

			if (socialActivitySetting != null) {
				session.delete(socialActivitySetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivitySetting != null) {
			clearCache(socialActivitySetting);
		}

		return socialActivitySetting;
	}

	@Override
	public SocialActivitySetting updateImpl(
		SocialActivitySetting socialActivitySetting) {

		boolean isNew = socialActivitySetting.isNew();

		if (!(socialActivitySetting instanceof
				SocialActivitySettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(socialActivitySetting.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					socialActivitySetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in socialActivitySetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SocialActivitySetting implementation " +
					socialActivitySetting.getClass());
		}

		SocialActivitySettingModelImpl socialActivitySettingModelImpl =
			(SocialActivitySettingModelImpl)socialActivitySetting;

		Session session = null;

		try {
			session = openSession();

			if (socialActivitySetting.isNew()) {
				session.save(socialActivitySetting);

				socialActivitySetting.setNew(false);
			}
			else {
				socialActivitySetting = (SocialActivitySetting)session.merge(
					socialActivitySetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SocialActivitySettingModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				socialActivitySettingModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				socialActivitySettingModelImpl.getGroupId(),
				socialActivitySettingModelImpl.getClassNameId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_C, args);

			args = new Object[] {
				socialActivitySettingModelImpl.getGroupId(),
				socialActivitySettingModelImpl.getActivityType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_A, args);

			args = new Object[] {
				socialActivitySettingModelImpl.getGroupId(),
				socialActivitySettingModelImpl.getClassNameId(),
				socialActivitySettingModelImpl.getActivityType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_C_A, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((socialActivitySettingModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialActivitySettingModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					socialActivitySettingModelImpl.getGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((socialActivitySettingModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivitySettingModelImpl.getOriginalGroupId(),
					socialActivitySettingModelImpl.getOriginalClassNameId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);

				args = new Object[] {
					socialActivitySettingModelImpl.getGroupId(),
					socialActivitySettingModelImpl.getClassNameId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);
			}

			if ((socialActivitySettingModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivitySettingModelImpl.getOriginalGroupId(),
					socialActivitySettingModelImpl.getOriginalActivityType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);

				args = new Object[] {
					socialActivitySettingModelImpl.getGroupId(),
					socialActivitySettingModelImpl.getActivityType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);
			}

			if ((socialActivitySettingModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialActivitySettingModelImpl.getOriginalGroupId(),
					socialActivitySettingModelImpl.getOriginalClassNameId(),
					socialActivitySettingModelImpl.getOriginalActivityType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_A, args);

				args = new Object[] {
					socialActivitySettingModelImpl.getGroupId(),
					socialActivitySettingModelImpl.getClassNameId(),
					socialActivitySettingModelImpl.getActivityType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_A, args);
			}
		}

		EntityCacheUtil.putResult(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			socialActivitySetting.getPrimaryKey(), socialActivitySetting,
			false);

		clearUniqueFindersCache(socialActivitySettingModelImpl, false);
		cacheUniqueFindersCache(socialActivitySettingModelImpl);

		socialActivitySetting.resetOriginalValues();

		return socialActivitySetting;
	}

	/**
	 * Returns the social activity setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity setting
	 * @return the social activity setting
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivitySettingException {

		SocialActivitySetting socialActivitySetting = fetchByPrimaryKey(
			primaryKey);

		if (socialActivitySetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivitySettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return socialActivitySetting;
	}

	/**
	 * Returns the social activity setting with the primary key or throws a <code>NoSuchActivitySettingException</code> if it could not be found.
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting
	 * @throws NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting findByPrimaryKey(long activitySettingId)
		throws NoSuchActivitySettingException {

		return findByPrimaryKey((Serializable)activitySettingId);
	}

	/**
	 * Returns the social activity setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting, or <code>null</code> if a social activity setting with the primary key could not be found
	 */
	@Override
	public SocialActivitySetting fetchByPrimaryKey(long activitySettingId) {
		return fetchByPrimaryKey((Serializable)activitySettingId);
	}

	/**
	 * Returns all the social activity settings.
	 *
	 * @return the social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findAll(
		int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of social activity settings
	 */
	@Override
	public List<SocialActivitySetting> findAll(
		int start, int end,
		OrderByComparator<SocialActivitySetting> orderByComparator,
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

		List<SocialActivitySetting> list = null;

		if (useFinderCache) {
			list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SOCIALACTIVITYSETTING);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYSETTING;

				sql = sql.concat(SocialActivitySettingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<SocialActivitySetting>)QueryUtil.list(
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
	 * Removes all the social activity settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SocialActivitySetting socialActivitySetting : findAll()) {
			remove(socialActivitySetting);
		}
	}

	/**
	 * Returns the number of social activity settings.
	 *
	 * @return the number of social activity settings
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYSETTING);

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
		return "activitySettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SOCIALACTIVITYSETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SocialActivitySettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the social activity setting persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_C = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.CLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByG_C = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_A = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] {Long.class.getName(), Integer.class.getName()},
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.ACTIVITYTYPE_COLUMN_BITMASK);

		_finderPathCountByG_A = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_C_A = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_A = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.ACTIVITYTYPE_COLUMN_BITMASK);

		_finderPathCountByG_C_A = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathFetchByG_C_A_N = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_A_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName()
			},
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.ACTIVITYTYPE_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_C_A_N = new FinderPath(
			SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_A_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SocialActivitySettingImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SOCIALACTIVITYSETTING =
		"SELECT socialActivitySetting FROM SocialActivitySetting socialActivitySetting";

	private static final String _SQL_SELECT_SOCIALACTIVITYSETTING_WHERE =
		"SELECT socialActivitySetting FROM SocialActivitySetting socialActivitySetting WHERE ";

	private static final String _SQL_COUNT_SOCIALACTIVITYSETTING =
		"SELECT COUNT(socialActivitySetting) FROM SocialActivitySetting socialActivitySetting";

	private static final String _SQL_COUNT_SOCIALACTIVITYSETTING_WHERE =
		"SELECT COUNT(socialActivitySetting) FROM SocialActivitySetting socialActivitySetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"socialActivitySetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SocialActivitySetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SocialActivitySetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivitySettingPersistenceImpl.class);

}