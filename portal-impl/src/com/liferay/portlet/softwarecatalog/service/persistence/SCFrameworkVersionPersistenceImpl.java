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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the s c framework version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionPersistence
 * @see SCFrameworkVersionUtil
 * @generated
 */
public class SCFrameworkVersionPersistenceImpl extends BasePersistenceImpl<SCFrameworkVersion>
	implements SCFrameworkVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCFrameworkVersionUtil} to access the s c framework version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCFrameworkVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SCFrameworkVersionModelImpl.GROUPID_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the s c framework versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
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

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCFrameworkVersion scFrameworkVersion : list) {
				if ((groupId != scFrameworkVersion.getGroupId())) {
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

			query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SCFrameworkVersion>(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Returns the first s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByGroupId_First(groupId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the first s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCFrameworkVersion> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<SCFrameworkVersion> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, scFrameworkVersion,
					groupId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByGroupId_PrevAndNext(session, scFrameworkVersion,
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

	protected SCFrameworkVersion getByGroupId_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> filterFindByGroupId(long groupId,
		int start, int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SCFrameworkVersion>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion[] filterFindByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(frameworkVersionId, groupId,
				orderByComparator);
		}

		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					scFrameworkVersion, groupId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					scFrameworkVersion, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion filterGetByGroupId_PrevAndNext(
		Session session, SCFrameworkVersion scFrameworkVersion, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c framework versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

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

	/**
	 * Returns the number of s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "scFrameworkVersion.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SCFrameworkVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the s c framework versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c framework versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
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

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCFrameworkVersion scFrameworkVersion : list) {
				if ((companyId != scFrameworkVersion.getCompanyId())) {
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

			query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SCFrameworkVersion>(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Returns the first s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the first s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCFrameworkVersion> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the last s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<SCFrameworkVersion> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, scFrameworkVersion,
					companyId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByCompanyId_PrevAndNext(session, scFrameworkVersion,
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

	protected SCFrameworkVersion getByCompanyId_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c framework versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "scFrameworkVersion.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			SCFrameworkVersionModelImpl.GROUPID_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.ACTIVE_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active)
		throws SystemException {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end) throws SystemException {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, active,
					
					start, end, orderByComparator
				};
		}

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCFrameworkVersion scFrameworkVersion : list) {
				if ((groupId != scFrameworkVersion.getGroupId()) ||
						(active != scFrameworkVersion.getActive())) {
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

			query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SCFrameworkVersion>(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion findByG_A_First(long groupId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByG_A_First(groupId,
				active, orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion fetchByG_A_First(long groupId, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCFrameworkVersion> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion findByG_A_Last(long groupId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByG_A_Last(groupId,
				active, orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_A(groupId, active);

		List<SCFrameworkVersion> list = findByG_A(groupId, active, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion[] findByG_A_PrevAndNext(long frameworkVersionId,
		long groupId, boolean active, OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion getByG_A_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> filterFindByG_A(long groupId, boolean active)
		throws SystemException {
		return filterFindByG_A(groupId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end) throws SystemException {
		return filterFindByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, active, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			return (List<SCFrameworkVersion>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion[] filterFindByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(frameworkVersionId, groupId, active,
				orderByComparator);
		}

		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = filterGetByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion filterGetByG_A_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c framework versions where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_A(long groupId, boolean active)
		throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByG_A(groupId, active,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_A(long groupId, boolean active)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

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

	/**
	 * Returns the number of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching s c framework versions that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_A(long groupId, boolean active)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, active);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "scFrameworkVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "scFrameworkVersion.active = ?";

	/**
	 * Caches the s c framework version in the entity cache if it is enabled.
	 *
	 * @param scFrameworkVersion the s c framework version
	 */
	public void cacheResult(SCFrameworkVersion scFrameworkVersion) {
		EntityCacheUtil.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion);

		scFrameworkVersion.resetOriginalValues();
	}

	/**
	 * Caches the s c framework versions in the entity cache if it is enabled.
	 *
	 * @param scFrameworkVersions the s c framework versions
	 */
	public void cacheResult(List<SCFrameworkVersion> scFrameworkVersions) {
		for (SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			if (EntityCacheUtil.getResult(
						SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCFrameworkVersionImpl.class,
						scFrameworkVersion.getPrimaryKey()) == null) {
				cacheResult(scFrameworkVersion);
			}
			else {
				scFrameworkVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c framework versions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SCFrameworkVersionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SCFrameworkVersionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c framework version.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCFrameworkVersion scFrameworkVersion) {
		EntityCacheUtil.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SCFrameworkVersion> scFrameworkVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			EntityCacheUtil.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey());
		}
	}

	/**
	 * Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	 *
	 * @param frameworkVersionId the primary key for the new s c framework version
	 * @return the new s c framework version
	 */
	public SCFrameworkVersion create(long frameworkVersionId) {
		SCFrameworkVersion scFrameworkVersion = new SCFrameworkVersionImpl();

		scFrameworkVersion.setNew(true);
		scFrameworkVersion.setPrimaryKey(frameworkVersionId);

		return scFrameworkVersion;
	}

	/**
	 * Removes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param frameworkVersionId the primary key of the s c framework version
	 * @return the s c framework version that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion remove(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		return remove(Long.valueOf(frameworkVersionId));
	}

	/**
	 * Removes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c framework version
	 * @return the s c framework version that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCFrameworkVersion remove(Serializable primaryKey)
		throws NoSuchFrameworkVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
					primaryKey);

			if (scFrameworkVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFrameworkVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scFrameworkVersion);
		}
		catch (NoSuchFrameworkVersionException nsee) {
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
	protected SCFrameworkVersion removeImpl(
		SCFrameworkVersion scFrameworkVersion) throws SystemException {
		scFrameworkVersion = toUnwrappedModel(scFrameworkVersion);

		try {
			clearSCProductVersions.clear(scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(scFrameworkVersion)) {
				scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
						scFrameworkVersion.getPrimaryKeyObj());
			}

			if (scFrameworkVersion != null) {
				session.delete(scFrameworkVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (scFrameworkVersion != null) {
			clearCache(scFrameworkVersion);
		}

		return scFrameworkVersion;
	}

	@Override
	public SCFrameworkVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		scFrameworkVersion = toUnwrappedModel(scFrameworkVersion);

		boolean isNew = scFrameworkVersion.isNew();

		SCFrameworkVersionModelImpl scFrameworkVersionModelImpl = (SCFrameworkVersionModelImpl)scFrameworkVersion;

		Session session = null;

		try {
			session = openSession();

			if (scFrameworkVersion.isNew()) {
				session.save(scFrameworkVersion);

				scFrameworkVersion.setNew(false);
			}
			else {
				session.merge(scFrameworkVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCFrameworkVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scFrameworkVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scFrameworkVersionModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(scFrameworkVersionModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((scFrameworkVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scFrameworkVersionModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(scFrameworkVersionModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((scFrameworkVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scFrameworkVersionModelImpl.getOriginalGroupId()),
						Boolean.valueOf(scFrameworkVersionModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						Long.valueOf(scFrameworkVersionModelImpl.getGroupId()),
						Boolean.valueOf(scFrameworkVersionModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		EntityCacheUtil.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion);

		return scFrameworkVersion;
	}

	protected SCFrameworkVersion toUnwrappedModel(
		SCFrameworkVersion scFrameworkVersion) {
		if (scFrameworkVersion instanceof SCFrameworkVersionImpl) {
			return scFrameworkVersion;
		}

		SCFrameworkVersionImpl scFrameworkVersionImpl = new SCFrameworkVersionImpl();

		scFrameworkVersionImpl.setNew(scFrameworkVersion.isNew());
		scFrameworkVersionImpl.setPrimaryKey(scFrameworkVersion.getPrimaryKey());

		scFrameworkVersionImpl.setFrameworkVersionId(scFrameworkVersion.getFrameworkVersionId());
		scFrameworkVersionImpl.setGroupId(scFrameworkVersion.getGroupId());
		scFrameworkVersionImpl.setCompanyId(scFrameworkVersion.getCompanyId());
		scFrameworkVersionImpl.setUserId(scFrameworkVersion.getUserId());
		scFrameworkVersionImpl.setUserName(scFrameworkVersion.getUserName());
		scFrameworkVersionImpl.setCreateDate(scFrameworkVersion.getCreateDate());
		scFrameworkVersionImpl.setModifiedDate(scFrameworkVersion.getModifiedDate());
		scFrameworkVersionImpl.setName(scFrameworkVersion.getName());
		scFrameworkVersionImpl.setUrl(scFrameworkVersion.getUrl());
		scFrameworkVersionImpl.setActive(scFrameworkVersion.isActive());
		scFrameworkVersionImpl.setPriority(scFrameworkVersion.getPriority());

		return scFrameworkVersionImpl;
	}

	/**
	 * Returns the s c framework version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c framework version
	 * @return the s c framework version
	 * @throws com.liferay.portal.NoSuchModelException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCFrameworkVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c framework version with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException} if it could not be found.
	 *
	 * @param frameworkVersionId the primary key of the s c framework version
	 * @return the s c framework version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion findByPrimaryKey(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByPrimaryKey(frameworkVersionId);

		if (scFrameworkVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					frameworkVersionId);
			}

			throw new NoSuchFrameworkVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				frameworkVersionId);
		}

		return scFrameworkVersion;
	}

	/**
	 * Returns the s c framework version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c framework version
	 * @return the s c framework version, or <code>null</code> if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCFrameworkVersion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c framework version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param frameworkVersionId the primary key of the s c framework version
	 * @return the s c framework version, or <code>null</code> if a s c framework version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCFrameworkVersion fetchByPrimaryKey(long frameworkVersionId)
		throws SystemException {
		SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)EntityCacheUtil.getResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCFrameworkVersionImpl.class, frameworkVersionId);

		if (scFrameworkVersion == _nullSCFrameworkVersion) {
			return null;
		}

		if (scFrameworkVersion == null) {
			Session session = null;

			try {
				session = openSession();

				scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
						Long.valueOf(frameworkVersionId));

				if (scFrameworkVersion != null) {
					cacheResult(scFrameworkVersion);
				}
				else {
					EntityCacheUtil.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCFrameworkVersionImpl.class, frameworkVersionId,
						_nullSCFrameworkVersion);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
					SCFrameworkVersionImpl.class, frameworkVersionId);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return scFrameworkVersion;
	}

	/**
	 * Returns all the s c framework versions.
	 *
	 * @return the s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCFrameworkVersion> findAll(int start, int end,
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

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCFRAMEWORKVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCFRAMEWORKVERSION;

				if (pagination) {
					sql = sql.concat(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SCFrameworkVersion>(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Removes all the s c framework versions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findAll()) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions.
	 *
	 * @return the number of s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCFRAMEWORKVERSION);

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
	 * Returns all the s c product versions associated with the s c framework version.
	 *
	 * @param pk the primary key of the s c framework version
	 * @return the s c product versions associated with the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk) throws SystemException {
		return getSCProductVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the s c product versions associated with the s c framework version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c framework version
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of s c product versions associated with the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end) throws SystemException {
		return getSCProductVersions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTVERSIONS = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl.class,
			SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCProductVersions",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

	static {
		FINDER_PATH_GET_SCPRODUCTVERSIONS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the s c product versions associated with the s c framework version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c framework version
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c product versions associated with the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { pk };
		}
		else {
			finderArgs = new Object[] { pk, start, end, orderByComparator };
		}

		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> list = (List<com.liferay.portlet.softwarecatalog.model.SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTVERSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETSCPRODUCTVERSIONS.concat(ORDER_BY_CLAUSE)
												   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETSCPRODUCTVERSIONS;

					if (pagination) {
						sql = sql.concat(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ORDER_BY_SQL);
					}
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCProductVersion",
					com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				if (!pagination) {
					list = (List<com.liferay.portlet.softwarecatalog.model.SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<com.liferay.portlet.softwarecatalog.model.SCProductVersion>(list);
				}
				else {
					list = (List<com.liferay.portlet.softwarecatalog.model.SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				scProductVersionPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTVERSIONS,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_SCPRODUCTVERSIONS,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			Long.class,
			SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCProductVersionsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of s c product versions associated with the s c framework version.
	 *
	 * @param pk the primary key of the s c framework version
	 * @return the number of s c product versions associated with the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public int getSCProductVersionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCPRODUCTVERSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCPRODUCTVERSION = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			Boolean.class,
			SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"containsSCProductVersion",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the s c product version is associated with the s c framework version.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPK the primary key of the s c product version
	 * @return <code>true</code> if the s c product version is associated with the s c framework version; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, scProductVersionPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCPRODUCTVERSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCProductVersion.contains(pk,
							scProductVersionPK));

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCPRODUCTVERSION,
					finderArgs, value);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_CONTAINS_SCPRODUCTVERSION,
					finderArgs);

				throw processException(e);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the s c framework version has any s c product versions associated with it.
	 *
	 * @param pk the primary key of the s c framework version to check for associations with s c product versions
	 * @return <code>true</code> if the s c framework version has any s c product versions associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsSCProductVersions(long pk) throws SystemException {
		if (getSCProductVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPK the primary key of the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		try {
			addSCProductVersion.add(pk, scProductVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersion the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws SystemException {
		try {
			addSCProductVersion.add(pk, scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPKs the primary keys of the s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			for (long scProductVersionPK : scProductVersionPKs) {
				addSCProductVersion.add(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersions the s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				addSCProductVersion.add(pk, scProductVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Clears all associations between the s c framework version and its s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version to clear the associated s c product versions from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearSCProductVersions(long pk) throws SystemException {
		try {
			clearSCProductVersions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPK the primary key of the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		try {
			removeSCProductVersion.remove(pk, scProductVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersion the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws SystemException {
		try {
			removeSCProductVersion.remove(pk, scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPKs the primary keys of the s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			for (long scProductVersionPK : scProductVersionPKs) {
				removeSCProductVersion.remove(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersions the s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				removeSCProductVersion.remove(pk,
					scProductVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPKs the primary keys of the s c product versions to be associated with the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public void setSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			Set<Long> scProductVersionPKSet = SetUtil.fromArray(scProductVersionPKs);

			List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions =
				getSCProductVersions(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				if (!scProductVersionPKSet.remove(
							scProductVersion.getPrimaryKey())) {
					removeSCProductVersion.remove(pk,
						scProductVersion.getPrimaryKey());
				}
			}

			for (Long scProductVersionPK : scProductVersionPKSet) {
				addSCProductVersion.add(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersions the s c product versions to be associated with the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public void setSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			long[] scProductVersionPKs = new long[scProductVersions.size()];

			for (int i = 0; i < scProductVersions.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion =
					scProductVersions.get(i);

				scProductVersionPKs[i] = scProductVersion.getPrimaryKey();
			}

			setSCProductVersions(pk, scProductVersionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Initializes the s c framework version persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCFrameworkVersion>> listenersList = new ArrayList<ModelListener<SCFrameworkVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCFrameworkVersion>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCProductVersion = new ContainsSCProductVersion();

		addSCProductVersion = new AddSCProductVersion();
		clearSCProductVersions = new ClearSCProductVersions();
		removeSCProductVersion = new RemoveSCProductVersion();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SCFrameworkVersionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = SCProductVersionPersistence.class)
	protected SCProductVersionPersistence scProductVersionPersistence;
	protected ContainsSCProductVersion containsSCProductVersion;
	protected AddSCProductVersion addSCProductVersion;
	protected ClearSCProductVersions clearSCProductVersions;
	protected RemoveSCProductVersion removeSCProductVersion;

	protected class ContainsSCProductVersion {
		protected ContainsSCProductVersion() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCPRODUCTVERSION,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long frameworkVersionId,
			long productVersionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddSCProductVersion {
		protected AddSCProductVersion() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCFrameworkVersi_SCProductVers (frameworkVersionId, productVersionId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long frameworkVersionId, long productVersionId)
			throws SystemException {
			if (!containsSCProductVersion.contains(frameworkVersionId,
						productVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
					scProductVersionPersistence.getListeners();

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onBeforeAddAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onBeforeAddAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onAfterAddAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onAfterAddAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearSCProductVersions {
		protected ClearSCProductVersions() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long frameworkVersionId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
				scProductVersionPersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions =
				null;

			if ((listeners.length > 0) ||
					(scProductVersionListeners.length > 0)) {
				scProductVersions = getSCProductVersions(frameworkVersionId);

				for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
					for (ModelListener<SCFrameworkVersion> listener : listeners) {
						listener.onBeforeRemoveAssociation(frameworkVersionId,
							com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
							scProductVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
						listener.onBeforeRemoveAssociation(scProductVersion.getPrimaryKey(),
							SCFrameworkVersion.class.getName(),
							frameworkVersionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(frameworkVersionId) });

			if ((listeners.length > 0) ||
					(scProductVersionListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
					for (ModelListener<SCFrameworkVersion> listener : listeners) {
						listener.onAfterRemoveAssociation(frameworkVersionId,
							com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
							scProductVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
						listener.onAfterRemoveAssociation(scProductVersion.getPrimaryKey(),
							SCFrameworkVersion.class.getName(),
							frameworkVersionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCProductVersion {
		protected RemoveSCProductVersion() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ? AND productVersionId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long frameworkVersionId, long productVersionId)
			throws SystemException {
			if (containsSCProductVersion.contains(frameworkVersionId,
						productVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
					scProductVersionPersistence.getListeners();

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onBeforeRemoveAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onBeforeRemoveAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onAfterRemoveAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onAfterRemoveAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_SCFRAMEWORKVERSION = "SELECT scFrameworkVersion FROM SCFrameworkVersion scFrameworkVersion";
	private static final String _SQL_SELECT_SCFRAMEWORKVERSION_WHERE = "SELECT scFrameworkVersion FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _SQL_COUNT_SCFRAMEWORKVERSION = "SELECT COUNT(scFrameworkVersion) FROM SCFrameworkVersion scFrameworkVersion";
	private static final String _SQL_COUNT_SCFRAMEWORKVERSION_WHERE = "SELECT COUNT(scFrameworkVersion) FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _SQL_GETSCPRODUCTVERSIONS = "SELECT {SCProductVersion.*} FROM SCProductVersion INNER JOIN SCFrameworkVersi_SCProductVers ON (SCFrameworkVersi_SCProductVers.productVersionId = SCProductVersion.productVersionId) WHERE (SCFrameworkVersi_SCProductVers.frameworkVersionId = ?)";
	private static final String _SQL_GETSCPRODUCTVERSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ?";
	private static final String _SQL_CONTAINSSCPRODUCTVERSION = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ? AND productVersionId = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "scFrameworkVersion.frameworkVersionId";
	private static final String _FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE = "SELECT DISTINCT {scFrameworkVersion.*} FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SCFrameworkVersion.*} FROM (SELECT DISTINCT scFrameworkVersion.frameworkVersionId FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SCFrameworkVersion ON TEMP_TABLE.frameworkVersionId = SCFrameworkVersion.frameworkVersionId";
	private static final String _FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE = "SELECT COUNT(DISTINCT scFrameworkVersion.frameworkVersionId) AS COUNT_VALUE FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "scFrameworkVersion";
	private static final String _FILTER_ENTITY_TABLE = "SCFrameworkVersion";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scFrameworkVersion.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SCFrameworkVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCFrameworkVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCFrameworkVersion exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SCFrameworkVersionPersistenceImpl.class);
	private static SCFrameworkVersion _nullSCFrameworkVersion = new SCFrameworkVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCFrameworkVersion> toCacheModel() {
				return _nullSCFrameworkVersionCacheModel;
			}
		};

	private static CacheModel<SCFrameworkVersion> _nullSCFrameworkVersionCacheModel =
		new CacheModel<SCFrameworkVersion>() {
			public SCFrameworkVersion toEntityModel() {
				return _nullSCFrameworkVersion;
			}
		};
}