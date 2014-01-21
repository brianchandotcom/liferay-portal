/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchOrgGroupRoleException;
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
import com.liferay.portal.model.OrgGroupRole;
import com.liferay.portal.model.impl.OrgGroupRoleImpl;
import com.liferay.portal.model.impl.OrgGroupRoleModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the org group role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupRolePersistence
 * @see OrgGroupRoleUtil
 * @generated
 */
public class OrgGroupRolePersistenceImpl extends BasePersistenceImpl<OrgGroupRole>
	implements OrgGroupRolePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OrgGroupRoleUtil} to access the org group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OrgGroupRoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, OrgGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, OrgGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, OrgGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, OrgGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			OrgGroupRoleModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the org group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of org group roles
	 * @param end the upper bound of the range of org group roles (not inclusive)
	 * @return the range of matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the org group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of org group roles
	 * @param end the upper bound of the range of org group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<OrgGroupRole> list = (List<OrgGroupRole>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (OrgGroupRole orgGroupRole : list) {
				if ((groupId != orgGroupRole.getGroupId())) {
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

			query.append(_SQL_SELECT_ORGGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OrgGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<OrgGroupRole>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<OrgGroupRole>(list);
				}
				else {
					list = (List<OrgGroupRole>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first org group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = fetchByGroupId_First(groupId,
				orderByComparator);

		if (orgGroupRole != null) {
			return orgGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrgGroupRoleException(msg.toString());
	}

	/**
	 * Returns the first org group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org group role, or <code>null</code> if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<OrgGroupRole> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last org group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (orgGroupRole != null) {
			return orgGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrgGroupRoleException(msg.toString());
	}

	/**
	 * Returns the last org group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org group role, or <code>null</code> if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<OrgGroupRole> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the org group roles before and after the current org group role in the ordered set where groupId = &#63;.
	 *
	 * @param orgGroupRolePK the primary key of the current org group role
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole[] findByGroupId_PrevAndNext(
		OrgGroupRolePK orgGroupRolePK, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = findByPrimaryKey(orgGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			OrgGroupRole[] array = new OrgGroupRoleImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, orgGroupRole, groupId,
					orderByComparator, true);

			array[1] = orgGroupRole;

			array[2] = getByGroupId_PrevAndNext(session, orgGroupRole, groupId,
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

	protected OrgGroupRole getByGroupId_PrevAndNext(Session session,
		OrgGroupRole orgGroupRole, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGGROUPROLE_WHERE);

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
			query.append(OrgGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(orgGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OrgGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the org group roles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByGroupId(long groupId) throws SystemException {
		for (OrgGroupRole orgGroupRole : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(orgGroupRole);
		}
	}

	/**
	 * Returns the number of org group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByGroupId(long groupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ORGGROUPROLE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "orgGroupRole.id.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ROLEID = new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, OrgGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID =
		new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, OrgGroupRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
			new String[] { Long.class.getName() },
			OrgGroupRoleModelImpl.ROLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRoleId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the org group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findByRoleId(long roleId)
		throws SystemException {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of org group roles
	 * @param end the upper bound of the range of org group roles (not inclusive)
	 * @return the range of matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the org group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of org group roles
	 * @param end the upper bound of the range of org group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findByRoleId(long roleId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID;
			finderArgs = new Object[] { roleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ROLEID;
			finderArgs = new Object[] { roleId, start, end, orderByComparator };
		}

		List<OrgGroupRole> list = (List<OrgGroupRole>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (OrgGroupRole orgGroupRole : list) {
				if ((roleId != orgGroupRole.getRoleId())) {
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

			query.append(_SQL_SELECT_ORGGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OrgGroupRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				if (!pagination) {
					list = (List<OrgGroupRole>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<OrgGroupRole>(list);
				}
				else {
					list = (List<OrgGroupRole>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first org group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole findByRoleId_First(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = fetchByRoleId_First(roleId,
				orderByComparator);

		if (orgGroupRole != null) {
			return orgGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrgGroupRoleException(msg.toString());
	}

	/**
	 * Returns the first org group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org group role, or <code>null</code> if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole fetchByRoleId_First(long roleId,
		OrderByComparator orderByComparator) throws SystemException {
		List<OrgGroupRole> list = findByRoleId(roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last org group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole findByRoleId_Last(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = fetchByRoleId_Last(roleId, orderByComparator);

		if (orgGroupRole != null) {
			return orgGroupRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrgGroupRoleException(msg.toString());
	}

	/**
	 * Returns the last org group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org group role, or <code>null</code> if a matching org group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole fetchByRoleId_Last(long roleId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<OrgGroupRole> list = findByRoleId(roleId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the org group roles before and after the current org group role in the ordered set where roleId = &#63;.
	 *
	 * @param orgGroupRolePK the primary key of the current org group role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole[] findByRoleId_PrevAndNext(
		OrgGroupRolePK orgGroupRolePK, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = findByPrimaryKey(orgGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			OrgGroupRole[] array = new OrgGroupRoleImpl[3];

			array[0] = getByRoleId_PrevAndNext(session, orgGroupRole, roleId,
					orderByComparator, true);

			array[1] = orgGroupRole;

			array[2] = getByRoleId_PrevAndNext(session, orgGroupRole, roleId,
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

	protected OrgGroupRole getByRoleId_PrevAndNext(Session session,
		OrgGroupRole orgGroupRole, long roleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

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
			query.append(OrgGroupRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(orgGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OrgGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the org group roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByRoleId(long roleId) throws SystemException {
		for (OrgGroupRole orgGroupRole : findByRoleId(roleId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(orgGroupRole);
		}
	}

	/**
	 * Returns the number of org group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByRoleId(long roleId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ROLEID;

		Object[] finderArgs = new Object[] { roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ORGGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

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

	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "orgGroupRole.id.roleId = ?";

	public OrgGroupRolePersistenceImpl() {
		setModelClass(OrgGroupRole.class);
	}

	/**
	 * Caches the org group role in the entity cache if it is enabled.
	 *
	 * @param orgGroupRole the org group role
	 */
	@Override
	public void cacheResult(OrgGroupRole orgGroupRole) {
		EntityCacheUtil.putResult(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleImpl.class, orgGroupRole.getPrimaryKey(), orgGroupRole);

		orgGroupRole.resetOriginalValues();
	}

	/**
	 * Caches the org group roles in the entity cache if it is enabled.
	 *
	 * @param orgGroupRoles the org group roles
	 */
	@Override
	public void cacheResult(List<OrgGroupRole> orgGroupRoles) {
		for (OrgGroupRole orgGroupRole : orgGroupRoles) {
			if (EntityCacheUtil.getResult(
						OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						OrgGroupRoleImpl.class, orgGroupRole.getPrimaryKey()) == null) {
				cacheResult(orgGroupRole);
			}
			else {
				orgGroupRole.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all org group roles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(OrgGroupRoleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(OrgGroupRoleImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the org group role.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OrgGroupRole orgGroupRole) {
		EntityCacheUtil.removeResult(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleImpl.class, orgGroupRole.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<OrgGroupRole> orgGroupRoles) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OrgGroupRole orgGroupRole : orgGroupRoles) {
			EntityCacheUtil.removeResult(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				OrgGroupRoleImpl.class, orgGroupRole.getPrimaryKey());
		}
	}

	/**
	 * Creates a new org group role with the primary key. Does not add the org group role to the database.
	 *
	 * @param orgGroupRolePK the primary key for the new org group role
	 * @return the new org group role
	 */
	@Override
	public OrgGroupRole create(OrgGroupRolePK orgGroupRolePK) {
		OrgGroupRole orgGroupRole = new OrgGroupRoleImpl();

		orgGroupRole.setNew(true);
		orgGroupRole.setPrimaryKey(orgGroupRolePK);

		return orgGroupRole;
	}

	/**
	 * Removes the org group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orgGroupRolePK the primary key of the org group role
	 * @return the org group role that was removed
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole remove(OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		return remove((Serializable)orgGroupRolePK);
	}

	/**
	 * Removes the org group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the org group role
	 * @return the org group role that was removed
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole remove(Serializable primaryKey)
		throws NoSuchOrgGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupRole orgGroupRole = (OrgGroupRole)session.get(OrgGroupRoleImpl.class,
					primaryKey);

			if (orgGroupRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrgGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(orgGroupRole);
		}
		catch (NoSuchOrgGroupRoleException nsee) {
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
	protected OrgGroupRole removeImpl(OrgGroupRole orgGroupRole)
		throws SystemException {
		orgGroupRole = toUnwrappedModel(orgGroupRole);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(orgGroupRole)) {
				orgGroupRole = (OrgGroupRole)session.get(OrgGroupRoleImpl.class,
						orgGroupRole.getPrimaryKeyObj());
			}

			if (orgGroupRole != null) {
				session.delete(orgGroupRole);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (orgGroupRole != null) {
			clearCache(orgGroupRole);
		}

		return orgGroupRole;
	}

	@Override
	public OrgGroupRole updateImpl(
		com.liferay.portal.model.OrgGroupRole orgGroupRole)
		throws SystemException {
		orgGroupRole = toUnwrappedModel(orgGroupRole);

		boolean isNew = orgGroupRole.isNew();

		OrgGroupRoleModelImpl orgGroupRoleModelImpl = (OrgGroupRoleModelImpl)orgGroupRole;

		Session session = null;

		try {
			session = openSession();

			if (orgGroupRole.isNew()) {
				session.save(orgGroupRole);

				orgGroupRole.setNew(false);
			}
			else {
				session.merge(orgGroupRole);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !OrgGroupRoleModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((orgGroupRoleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						orgGroupRoleModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { orgGroupRoleModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((orgGroupRoleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						orgGroupRoleModelImpl.getOriginalRoleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);

				args = new Object[] { orgGroupRoleModelImpl.getRoleId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);
			}
		}

		EntityCacheUtil.putResult(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupRoleImpl.class, orgGroupRole.getPrimaryKey(), orgGroupRole);

		orgGroupRole.resetOriginalValues();

		return orgGroupRole;
	}

	protected OrgGroupRole toUnwrappedModel(OrgGroupRole orgGroupRole) {
		if (orgGroupRole instanceof OrgGroupRoleImpl) {
			return orgGroupRole;
		}

		OrgGroupRoleImpl orgGroupRoleImpl = new OrgGroupRoleImpl();

		orgGroupRoleImpl.setNew(orgGroupRole.isNew());
		orgGroupRoleImpl.setPrimaryKey(orgGroupRole.getPrimaryKey());

		orgGroupRoleImpl.setOrganizationId(orgGroupRole.getOrganizationId());
		orgGroupRoleImpl.setGroupId(orgGroupRole.getGroupId());
		orgGroupRoleImpl.setRoleId(orgGroupRole.getRoleId());
		orgGroupRoleImpl.setMvccVersion(orgGroupRole.getMvccVersion());

		return orgGroupRoleImpl;
	}

	/**
	 * Returns the org group role with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the org group role
	 * @return the org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = fetchByPrimaryKey(primaryKey);

		if (orgGroupRole == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrgGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return orgGroupRole;
	}

	/**
	 * Returns the org group role with the primary key or throws a {@link com.liferay.portal.NoSuchOrgGroupRoleException} if it could not be found.
	 *
	 * @param orgGroupRolePK the primary key of the org group role
	 * @return the org group role
	 * @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole findByPrimaryKey(OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		return findByPrimaryKey((Serializable)orgGroupRolePK);
	}

	/**
	 * Returns the org group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the org group role
	 * @return the org group role, or <code>null</code> if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		OrgGroupRole orgGroupRole = (OrgGroupRole)EntityCacheUtil.getResult(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				OrgGroupRoleImpl.class, primaryKey);

		if (orgGroupRole == _nullOrgGroupRole) {
			return null;
		}

		if (orgGroupRole == null) {
			Session session = null;

			try {
				session = openSession();

				orgGroupRole = (OrgGroupRole)session.get(OrgGroupRoleImpl.class,
						primaryKey);

				if (orgGroupRole != null) {
					cacheResult(orgGroupRole);
				}
				else {
					EntityCacheUtil.putResult(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						OrgGroupRoleImpl.class, primaryKey, _nullOrgGroupRole);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(OrgGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
					OrgGroupRoleImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return orgGroupRole;
	}

	/**
	 * Returns the org group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param orgGroupRolePK the primary key of the org group role
	 * @return the org group role, or <code>null</code> if a org group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupRole fetchByPrimaryKey(OrgGroupRolePK orgGroupRolePK)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)orgGroupRolePK);
	}

	/**
	 * Returns all the org group roles.
	 *
	 * @return the org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of org group roles
	 * @param end the upper bound of the range of org group roles (not inclusive)
	 * @return the range of org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the org group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of org group roles
	 * @param end the upper bound of the range of org group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of org group roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<OrgGroupRole> findAll(int start, int end,
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

		List<OrgGroupRole> list = (List<OrgGroupRole>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ORGGROUPROLE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ORGGROUPROLE;

				if (pagination) {
					sql = sql.concat(OrgGroupRoleModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<OrgGroupRole>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<OrgGroupRole>(list);
				}
				else {
					list = (List<OrgGroupRole>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the org group roles from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (OrgGroupRole orgGroupRole : findAll()) {
			remove(orgGroupRole);
		}
	}

	/**
	 * Returns the number of org group roles.
	 *
	 * @return the number of org group roles
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

				Query q = session.createQuery(_SQL_COUNT_ORGGROUPROLE);

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
	 * Initializes the org group role persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.OrgGroupRole")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<OrgGroupRole>> listenersList = new ArrayList<ModelListener<OrgGroupRole>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<OrgGroupRole>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(OrgGroupRoleImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_ORGGROUPROLE = "SELECT orgGroupRole FROM OrgGroupRole orgGroupRole";
	private static final String _SQL_SELECT_ORGGROUPROLE_WHERE = "SELECT orgGroupRole FROM OrgGroupRole orgGroupRole WHERE ";
	private static final String _SQL_COUNT_ORGGROUPROLE = "SELECT COUNT(orgGroupRole) FROM OrgGroupRole orgGroupRole";
	private static final String _SQL_COUNT_ORGGROUPROLE_WHERE = "SELECT COUNT(orgGroupRole) FROM OrgGroupRole orgGroupRole WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "orgGroupRole.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OrgGroupRole exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OrgGroupRole exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(OrgGroupRolePersistenceImpl.class);
	private static OrgGroupRole _nullOrgGroupRole = new OrgGroupRoleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<OrgGroupRole> toCacheModel() {
				return _nullOrgGroupRoleCacheModel;
			}
		};

	private static CacheModel<OrgGroupRole> _nullOrgGroupRoleCacheModel = new CacheModel<OrgGroupRole>() {
			@Override
			public OrgGroupRole toEntityModel() {
				return _nullOrgGroupRole;
			}
		};
}