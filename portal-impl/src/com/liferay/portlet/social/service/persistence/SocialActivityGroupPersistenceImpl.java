/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchActivityGroupException;
import com.liferay.portlet.social.model.SocialActivityGroup;
import com.liferay.portlet.social.model.impl.SocialActivityGroupImpl;
import com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social activity group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityGroupPersistence
 * @see SocialActivityGroupUtil
 * @generated
 */
public class SocialActivityGroupPersistenceImpl extends BasePersistenceImpl<SocialActivityGroup>
	implements SocialActivityGroupPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivityGroupUtil} to access the social activity group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivityGroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SocialActivityGroupModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the social activity groups where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((groupId != socialActivityGroup.getGroupId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByGroupId_First(groupId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityGroup> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<SocialActivityGroup> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where groupId = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByGroupId_PrevAndNext(
		long activityGroupId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, socialActivityGroup,
					groupId, orderByComparator, true);

			array[1] = socialActivityGroup;

			array[2] = getByGroupId_PrevAndNext(session, socialActivityGroup,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityGroup getByGroupId_PrevAndNext(Session session,
		SocialActivityGroup socialActivityGroup, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "socialActivityGroup.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SocialActivityGroupModelImpl.COMPANYID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the social activity groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social activity groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((companyId != socialActivityGroup.getCompanyId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityGroup> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<SocialActivityGroup> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where companyId = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByCompanyId_PrevAndNext(
		long activityGroupId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, socialActivityGroup,
					companyId, orderByComparator, true);

			array[1] = socialActivityGroup;

			array[2] = getByCompanyId_PrevAndNext(session, socialActivityGroup,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityGroup getByCompanyId_PrevAndNext(Session session,
		SocialActivityGroup socialActivityGroup, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "socialActivityGroup.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SocialActivityGroupModelImpl.USERID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the social activity groups where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((userId != socialActivityGroup.getUserId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByUserId_First(userId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityGroup> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByUserId_Last(userId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<SocialActivityGroup> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where userId = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByUserId_PrevAndNext(
		long activityGroupId, long userId, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByUserId_PrevAndNext(session, socialActivityGroup,
					userId, orderByComparator, true);

			array[1] = socialActivityGroup;

			array[2] = getByUserId_PrevAndNext(session, socialActivityGroup,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityGroup getByUserId_PrevAndNext(Session session,
		SocialActivityGroup socialActivityGroup, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialActivityGroup.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassNameId",
			new String[] { Long.class.getName() },
			SocialActivityGroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassNameId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the social activity groups where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByClassNameId(long classNameId)
		throws SystemException {
		return findByClassNameId(classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByClassNameId(long classNameId,
		int start, int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByClassNameId(long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId, start, end, orderByComparator };
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((classNameId != socialActivityGroup.getClassNameId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByClassNameId_First(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByClassNameId_First(classNameId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByClassNameId_First(long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityGroup> list = findByClassNameId(classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByClassNameId_Last(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByClassNameId_Last(classNameId,
				orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByClassNameId_Last(long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByClassNameId(classNameId);

		List<SocialActivityGroup> list = findByClassNameId(classNameId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where classNameId = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByClassNameId_PrevAndNext(
		long activityGroupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByClassNameId_PrevAndNext(session,
					socialActivityGroup, classNameId, orderByComparator, true);

			array[1] = socialActivityGroup;

			array[2] = getByClassNameId_PrevAndNext(session,
					socialActivityGroup, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityGroup getByClassNameId_PrevAndNext(
		Session session, SocialActivityGroup socialActivityGroup,
		long classNameId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByClassNameId(long classNameId) throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByClassNameId(
				classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByClassNameId(long classNameId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CLASSNAMEID;

		Object[] finderArgs = new Object[] { classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 = "socialActivityGroup.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SocialActivityGroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the social activity groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByC_C(long classNameId, long classPK)
		throws SystemException {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((classNameId != socialActivityGroup.getClassNameId()) ||
						(classPK != socialActivityGroup.getClassPK())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityGroup> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<SocialActivityGroup> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByC_C_PrevAndNext(long activityGroupId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByC_C_PrevAndNext(session, socialActivityGroup,
					classNameId, classPK, orderByComparator, true);

			array[1] = socialActivityGroup;

			array[2] = getByC_C_PrevAndNext(session, socialActivityGroup,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityGroup getByC_C_PrevAndNext(Session session,
		SocialActivityGroup socialActivityGroup, long classNameId,
		long classPK, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByC_C(classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "socialActivityGroup.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "socialActivityGroup.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_T = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivityGroupModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.USERID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_C_T = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_T(long groupId, long userId,
		long classNameId, int type) throws SystemException {
		return findByG_U_C_T(groupId, userId, classNameId, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_T(long groupId, long userId,
		long classNameId, int type, int start, int end)
		throws SystemException {
		return findByG_U_C_T(groupId, userId, classNameId, type, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_T(long groupId, long userId,
		long classNameId, int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T;
			finderArgs = new Object[] { groupId, userId, classNameId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_T;
			finderArgs = new Object[] {
					groupId, userId, classNameId, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((groupId != socialActivityGroup.getGroupId()) ||
						(userId != socialActivityGroup.getUserId()) ||
						(classNameId != socialActivityGroup.getClassNameId()) ||
						(type != socialActivityGroup.getType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(type);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByG_U_C_T_First(long groupId, long userId,
		long classNameId, int type, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByG_U_C_T_First(groupId,
				userId, classNameId, type, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByG_U_C_T_First(long groupId, long userId,
		long classNameId, int type, OrderByComparator orderByComparator)
		throws SystemException {
		List<SocialActivityGroup> list = findByG_U_C_T(groupId, userId,
				classNameId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByG_U_C_T_Last(long groupId, long userId,
		long classNameId, int type, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByG_U_C_T_Last(groupId,
				userId, classNameId, type, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByG_U_C_T_Last(long groupId, long userId,
		long classNameId, int type, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_U_C_T(groupId, userId, classNameId, type);

		List<SocialActivityGroup> list = findByG_U_C_T(groupId, userId,
				classNameId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByG_U_C_T_PrevAndNext(
		long activityGroupId, long groupId, long userId, long classNameId,
		int type, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByG_U_C_T_PrevAndNext(session, socialActivityGroup,
					groupId, userId, classNameId, type, orderByComparator, true);

			array[1] = socialActivityGroup;

			array[2] = getByG_U_C_T_PrevAndNext(session, socialActivityGroup,
					groupId, userId, classNameId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityGroup getByG_U_C_T_PrevAndNext(Session session,
		SocialActivityGroup socialActivityGroup, long groupId, long userId,
		long classNameId, int type, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_C_T(long groupId, long userId, long classNameId,
		int type) throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByG_U_C_T(groupId,
				userId, classNameId, type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_C_T(long groupId, long userId, long classNameId,
		int type) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_C_T;

		Object[] finderArgs = new Object[] { groupId, userId, classNameId, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_U_C_T_GROUPID_2 = "socialActivityGroup.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_T_USERID_2 = "socialActivityGroup.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2 = "socialActivityGroup.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_T_TYPE_2 = "socialActivityGroup.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_C = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			SocialActivityGroupModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.USERID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_C_C = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_C(long groupId, long userId,
		long classNameId, long classPK) throws SystemException {
		return findByG_U_C_C(groupId, userId, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_C(long groupId, long userId,
		long classNameId, long classPK, int start, int end)
		throws SystemException {
		return findByG_U_C_C(groupId, userId, classNameId, classPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_C(long groupId, long userId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C;
			finderArgs = new Object[] { groupId, userId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_C;
			finderArgs = new Object[] {
					groupId, userId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((groupId != socialActivityGroup.getGroupId()) ||
						(userId != socialActivityGroup.getUserId()) ||
						(classNameId != socialActivityGroup.getClassNameId()) ||
						(classPK != socialActivityGroup.getClassPK())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByG_U_C_C_First(long groupId, long userId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByG_U_C_C_First(groupId,
				userId, classNameId, classPK, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByG_U_C_C_First(long groupId, long userId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		List<SocialActivityGroup> list = findByG_U_C_C(groupId, userId,
				classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByG_U_C_C_Last(long groupId, long userId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByG_U_C_C_Last(groupId,
				userId, classNameId, classPK, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByG_U_C_C_Last(long groupId, long userId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_U_C_C(groupId, userId, classNameId, classPK);

		List<SocialActivityGroup> list = findByG_U_C_C(groupId, userId,
				classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByG_U_C_C_PrevAndNext(
		long activityGroupId, long groupId, long userId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByG_U_C_C_PrevAndNext(session, socialActivityGroup,
					groupId, userId, classNameId, classPK, orderByComparator,
					true);

			array[1] = socialActivityGroup;

			array[2] = getByG_U_C_C_PrevAndNext(session, socialActivityGroup,
					groupId, userId, classNameId, classPK, orderByComparator,
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

	protected SocialActivityGroup getByG_U_C_C_PrevAndNext(Session session,
		SocialActivityGroup socialActivityGroup, long groupId, long userId,
		long classNameId, long classPK, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_G_U_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_C_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_U_C_C_CLASSPK_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_C_C(long groupId, long userId, long classNameId,
		long classPK) throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByG_U_C_C(groupId,
				userId, classNameId, classPK, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_C_C(long groupId, long userId, long classNameId,
		long classPK) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_C_C;

		Object[] finderArgs = new Object[] { groupId, userId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_U_C_C_GROUPID_2 = "socialActivityGroup.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_USERID_2 = "socialActivityGroup.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_CLASSNAMEID_2 = "socialActivityGroup.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_CLASSPK_2 = "socialActivityGroup.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_C_T =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C_T =
		new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			SocialActivityGroupModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.USERID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivityGroupModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_C_C_T = new FinderPath(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_C_T(long groupId, long userId,
		long classNameId, long classPK, int type) throws SystemException {
		return findByG_U_C_C_T(groupId, userId, classNameId, classPK, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_C_T(long groupId, long userId,
		long classNameId, long classPK, int type, int start, int end)
		throws SystemException {
		return findByG_U_C_C_T(groupId, userId, classNameId, classPK, type,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findByG_U_C_C_T(long groupId, long userId,
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C_T;
			finderArgs = new Object[] {
					groupId, userId, classNameId, classPK, type
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_C_T;
			finderArgs = new Object[] {
					groupId, userId, classNameId, classPK, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityGroup socialActivityGroup : list) {
				if ((groupId != socialActivityGroup.getGroupId()) ||
						(userId != socialActivityGroup.getUserId()) ||
						(classNameId != socialActivityGroup.getClassNameId()) ||
						(classPK != socialActivityGroup.getClassPK()) ||
						(type != socialActivityGroup.getType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(7 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Returns the first social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByG_U_C_C_T_First(long groupId, long userId,
		long classNameId, long classPK, int type,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByG_U_C_C_T_First(groupId,
				userId, classNameId, classPK, type, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the first social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByG_U_C_C_T_First(long groupId,
		long userId, long classNameId, long classPK, int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityGroup> list = findByG_U_C_C_T(groupId, userId,
				classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByG_U_C_C_T_Last(long groupId, long userId,
		long classNameId, long classPK, int type,
		OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByG_U_C_C_T_Last(groupId,
				userId, classNameId, classPK, type, orderByComparator);

		if (socialActivityGroup != null) {
			return socialActivityGroup;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityGroupException(msg.toString());
	}

	/**
	 * Returns the last social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity group, or <code>null</code> if a matching social activity group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByG_U_C_C_T_Last(long groupId, long userId,
		long classNameId, long classPK, int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_U_C_C_T(groupId, userId, classNameId, classPK, type);

		List<SocialActivityGroup> list = findByG_U_C_C_T(groupId, userId,
				classNameId, classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity groups before and after the current social activity group in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param activityGroupId the primary key of the current social activity group
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup[] findByG_U_C_C_T_PrevAndNext(
		long activityGroupId, long groupId, long userId, long classNameId,
		long classPK, int type, OrderByComparator orderByComparator)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = findByPrimaryKey(activityGroupId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup[] array = new SocialActivityGroupImpl[3];

			array[0] = getByG_U_C_C_T_PrevAndNext(session, socialActivityGroup,
					groupId, userId, classNameId, classPK, type,
					orderByComparator, true);

			array[1] = socialActivityGroup;

			array[2] = getByG_U_C_C_T_PrevAndNext(session, socialActivityGroup,
					groupId, userId, classNameId, classPK, type,
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

	protected SocialActivityGroup getByG_U_C_C_T_PrevAndNext(Session session,
		SocialActivityGroup socialActivityGroup, long groupId, long userId,
		long classNameId, long classPK, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYGROUP_WHERE);

		query.append(_FINDER_COLUMN_G_U_C_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_C_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_U_C_C_T_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_U_C_C_T_TYPE_2);

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
			query.append(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_C_C_T(long groupId, long userId, long classNameId,
		long classPK, int type) throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findByG_U_C_C_T(
				groupId, userId, classNameId, classPK, type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_C_C_T(long groupId, long userId, long classNameId,
		long classPK, int type) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_C_C_T;

		Object[] finderArgs = new Object[] {
				groupId, userId, classNameId, classPK, type
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_SOCIALACTIVITYGROUP_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_C_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_U_C_C_T_GROUPID_2 = "socialActivityGroup.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_T_USERID_2 = "socialActivityGroup.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_T_CLASSNAMEID_2 = "socialActivityGroup.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_T_CLASSPK_2 = "socialActivityGroup.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_T_TYPE_2 = "socialActivityGroup.type = ?";

	/**
	 * Caches the social activity group in the entity cache if it is enabled.
	 *
	 * @param socialActivityGroup the social activity group
	 */
	public void cacheResult(SocialActivityGroup socialActivityGroup) {
		EntityCacheUtil.putResult(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupImpl.class, socialActivityGroup.getPrimaryKey(),
			socialActivityGroup);

		socialActivityGroup.resetOriginalValues();
	}

	/**
	 * Caches the social activity groups in the entity cache if it is enabled.
	 *
	 * @param socialActivityGroups the social activity groups
	 */
	public void cacheResult(List<SocialActivityGroup> socialActivityGroups) {
		for (SocialActivityGroup socialActivityGroup : socialActivityGroups) {
			if (EntityCacheUtil.getResult(
						SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityGroupImpl.class,
						socialActivityGroup.getPrimaryKey()) == null) {
				cacheResult(socialActivityGroup);
			}
			else {
				socialActivityGroup.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity groups.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialActivityGroupImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialActivityGroupImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity group.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivityGroup socialActivityGroup) {
		EntityCacheUtil.removeResult(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupImpl.class, socialActivityGroup.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SocialActivityGroup> socialActivityGroups) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivityGroup socialActivityGroup : socialActivityGroups) {
			EntityCacheUtil.removeResult(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityGroupImpl.class,
				socialActivityGroup.getPrimaryKey());
		}
	}

	/**
	 * Creates a new social activity group with the primary key. Does not add the social activity group to the database.
	 *
	 * @param activityGroupId the primary key for the new social activity group
	 * @return the new social activity group
	 */
	public SocialActivityGroup create(long activityGroupId) {
		SocialActivityGroup socialActivityGroup = new SocialActivityGroupImpl();

		socialActivityGroup.setNew(true);
		socialActivityGroup.setPrimaryKey(activityGroupId);

		return socialActivityGroup;
	}

	/**
	 * Removes the social activity group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityGroupId the primary key of the social activity group
	 * @return the social activity group that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup remove(long activityGroupId)
		throws NoSuchActivityGroupException, SystemException {
		return remove((Serializable)activityGroupId);
	}

	/**
	 * Removes the social activity group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity group
	 * @return the social activity group that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityGroup remove(Serializable primaryKey)
		throws NoSuchActivityGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivityGroup socialActivityGroup = (SocialActivityGroup)session.get(SocialActivityGroupImpl.class,
					primaryKey);

			if (socialActivityGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivityGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialActivityGroup);
		}
		catch (NoSuchActivityGroupException nsee) {
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
	protected SocialActivityGroup removeImpl(
		SocialActivityGroup socialActivityGroup) throws SystemException {
		socialActivityGroup = toUnwrappedModel(socialActivityGroup);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivityGroup)) {
				socialActivityGroup = (SocialActivityGroup)session.get(SocialActivityGroupImpl.class,
						socialActivityGroup.getPrimaryKeyObj());
			}

			if (socialActivityGroup != null) {
				session.delete(socialActivityGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivityGroup != null) {
			clearCache(socialActivityGroup);
		}

		return socialActivityGroup;
	}

	@Override
	public SocialActivityGroup updateImpl(
		com.liferay.portlet.social.model.SocialActivityGroup socialActivityGroup)
		throws SystemException {
		socialActivityGroup = toUnwrappedModel(socialActivityGroup);

		boolean isNew = socialActivityGroup.isNew();

		SocialActivityGroupModelImpl socialActivityGroupModelImpl = (SocialActivityGroupModelImpl)socialActivityGroup;

		Session session = null;

		try {
			session = openSession();

			if (socialActivityGroup.isNew()) {
				session.save(socialActivityGroup);

				socialActivityGroup.setNew(false);
			}
			else {
				session.merge(socialActivityGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialActivityGroupModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { socialActivityGroupModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { socialActivityGroupModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { socialActivityGroupModelImpl.getUserId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);

				args = new Object[] {
						socialActivityGroupModelImpl.getClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);
			}

			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalClassNameId(),
						socialActivityGroupModelImpl.getOriginalClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						socialActivityGroupModelImpl.getClassNameId(),
						socialActivityGroupModelImpl.getClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalGroupId(),
						socialActivityGroupModelImpl.getOriginalUserId(),
						socialActivityGroupModelImpl.getOriginalClassNameId(),
						socialActivityGroupModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T,
					args);

				args = new Object[] {
						socialActivityGroupModelImpl.getGroupId(),
						socialActivityGroupModelImpl.getUserId(),
						socialActivityGroupModelImpl.getClassNameId(),
						socialActivityGroupModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T,
					args);
			}

			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalGroupId(),
						socialActivityGroupModelImpl.getOriginalUserId(),
						socialActivityGroupModelImpl.getOriginalClassNameId(),
						socialActivityGroupModelImpl.getOriginalClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C,
					args);

				args = new Object[] {
						socialActivityGroupModelImpl.getGroupId(),
						socialActivityGroupModelImpl.getUserId(),
						socialActivityGroupModelImpl.getClassNameId(),
						socialActivityGroupModelImpl.getClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C,
					args);
			}

			if ((socialActivityGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityGroupModelImpl.getOriginalGroupId(),
						socialActivityGroupModelImpl.getOriginalUserId(),
						socialActivityGroupModelImpl.getOriginalClassNameId(),
						socialActivityGroupModelImpl.getOriginalClassPK(),
						socialActivityGroupModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C_C_T,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C_T,
					args);

				args = new Object[] {
						socialActivityGroupModelImpl.getGroupId(),
						socialActivityGroupModelImpl.getUserId(),
						socialActivityGroupModelImpl.getClassNameId(),
						socialActivityGroupModelImpl.getClassPK(),
						socialActivityGroupModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C_C_T,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_C_T,
					args);
			}
		}

		EntityCacheUtil.putResult(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityGroupImpl.class, socialActivityGroup.getPrimaryKey(),
			socialActivityGroup);

		return socialActivityGroup;
	}

	protected SocialActivityGroup toUnwrappedModel(
		SocialActivityGroup socialActivityGroup) {
		if (socialActivityGroup instanceof SocialActivityGroupImpl) {
			return socialActivityGroup;
		}

		SocialActivityGroupImpl socialActivityGroupImpl = new SocialActivityGroupImpl();

		socialActivityGroupImpl.setNew(socialActivityGroup.isNew());
		socialActivityGroupImpl.setPrimaryKey(socialActivityGroup.getPrimaryKey());

		socialActivityGroupImpl.setActivityGroupId(socialActivityGroup.getActivityGroupId());
		socialActivityGroupImpl.setGroupId(socialActivityGroup.getGroupId());
		socialActivityGroupImpl.setCompanyId(socialActivityGroup.getCompanyId());
		socialActivityGroupImpl.setUserId(socialActivityGroup.getUserId());
		socialActivityGroupImpl.setCreateDate(socialActivityGroup.getCreateDate());
		socialActivityGroupImpl.setModifiedDate(socialActivityGroup.getModifiedDate());
		socialActivityGroupImpl.setClassNameId(socialActivityGroup.getClassNameId());
		socialActivityGroupImpl.setClassPK(socialActivityGroup.getClassPK());
		socialActivityGroupImpl.setType(socialActivityGroup.getType());

		return socialActivityGroupImpl;
	}

	/**
	 * Returns the social activity group with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity group
	 * @return the social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivityGroupException, SystemException {
		SocialActivityGroup socialActivityGroup = fetchByPrimaryKey(primaryKey);

		if (socialActivityGroup == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivityGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return socialActivityGroup;
	}

	/**
	 * Returns the social activity group with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityGroupException} if it could not be found.
	 *
	 * @param activityGroupId the primary key of the social activity group
	 * @return the social activity group
	 * @throws com.liferay.portlet.social.NoSuchActivityGroupException if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup findByPrimaryKey(long activityGroupId)
		throws NoSuchActivityGroupException, SystemException {
		return findByPrimaryKey((Serializable)activityGroupId);
	}

	/**
	 * Returns the social activity group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity group
	 * @return the social activity group, or <code>null</code> if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityGroup fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		SocialActivityGroup socialActivityGroup = (SocialActivityGroup)EntityCacheUtil.getResult(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityGroupImpl.class, primaryKey);

		if (socialActivityGroup == _nullSocialActivityGroup) {
			return null;
		}

		if (socialActivityGroup == null) {
			Session session = null;

			try {
				session = openSession();

				socialActivityGroup = (SocialActivityGroup)session.get(SocialActivityGroupImpl.class,
						primaryKey);

				if (socialActivityGroup != null) {
					cacheResult(socialActivityGroup);
				}
				else {
					EntityCacheUtil.putResult(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityGroupImpl.class, primaryKey,
						_nullSocialActivityGroup);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(SocialActivityGroupModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivityGroupImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return socialActivityGroup;
	}

	/**
	 * Returns the social activity group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityGroupId the primary key of the social activity group
	 * @return the social activity group, or <code>null</code> if a social activity group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityGroup fetchByPrimaryKey(long activityGroupId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)activityGroupId);
	}

	/**
	 * Returns all the social activity groups.
	 *
	 * @return the social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @return the range of social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity groups
	 * @param end the upper bound of the range of social activity groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityGroup> findAll(int start, int end,
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

		List<SocialActivityGroup> list = (List<SocialActivityGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYGROUP);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYGROUP;

				if (pagination) {
					sql = sql.concat(SocialActivityGroupModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivityGroup>(list);
				}
				else {
					list = (List<SocialActivityGroup>)QueryUtil.list(q,
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
	 * Removes all the social activity groups from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialActivityGroup socialActivityGroup : findAll()) {
			remove(socialActivityGroup);
		}
	}

	/**
	 * Returns the number of social activity groups.
	 *
	 * @return the number of social activity groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYGROUP);

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

	/**
	 * Initializes the social activity group persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialActivityGroup")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialActivityGroup>> listenersList = new ArrayList<ModelListener<SocialActivityGroup>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialActivityGroup>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SocialActivityGroupImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SOCIALACTIVITYGROUP = "SELECT socialActivityGroup FROM SocialActivityGroup socialActivityGroup";
	private static final String _SQL_SELECT_SOCIALACTIVITYGROUP_WHERE = "SELECT socialActivityGroup FROM SocialActivityGroup socialActivityGroup WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYGROUP = "SELECT COUNT(socialActivityGroup) FROM SocialActivityGroup socialActivityGroup";
	private static final String _SQL_COUNT_SOCIALACTIVITYGROUP_WHERE = "SELECT COUNT(socialActivityGroup) FROM SocialActivityGroup socialActivityGroup WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivityGroup.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivityGroup exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialActivityGroup exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialActivityGroupPersistenceImpl.class);
	private static SocialActivityGroup _nullSocialActivityGroup = new SocialActivityGroupImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivityGroup> toCacheModel() {
				return _nullSocialActivityGroupCacheModel;
			}
		};

	private static CacheModel<SocialActivityGroup> _nullSocialActivityGroupCacheModel =
		new CacheModel<SocialActivityGroup>() {
			public SocialActivityGroup toEntityModel() {
				return _nullSocialActivityGroup;
			}
		};
}