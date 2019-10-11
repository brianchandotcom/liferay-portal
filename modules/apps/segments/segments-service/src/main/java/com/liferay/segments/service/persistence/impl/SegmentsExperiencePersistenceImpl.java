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

package com.liferay.segments.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.segments.exception.NoSuchExperienceException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.impl.SegmentsExperienceImpl;
import com.liferay.segments.model.impl.SegmentsExperienceModelImpl;
import com.liferay.segments.service.persistence.SegmentsExperiencePersistence;
import com.liferay.segments.service.persistence.impl.constants.SegmentsPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the segments experience service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @generated
 */
@Component(service = SegmentsExperiencePersistence.class)
public class SegmentsExperiencePersistenceImpl
	extends BasePersistenceImpl<SegmentsExperience>
	implements SegmentsExperiencePersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SegmentsExperienceUtil</code> to access the segments experience persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SegmentsExperienceImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the segments experiences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if (!uuid.equals(segmentsExperience.getUuid())) {
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

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByUuid_First(
			String uuid,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByUuid_First(
			uuid, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByUuid_First(
		String uuid, OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByUuid_Last(
			String uuid,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByUuid_Last(
			uuid, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByUuid_Last(
		String uuid, OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where uuid = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByUuid_PrevAndNext(
			long segmentsExperienceId, String uuid,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		uuid = Objects.toString(uuid, "");

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, segmentsExperience, uuid, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByUuid_PrevAndNext(
				session, segmentsExperience, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience getByUuid_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, String uuid,
		OrderByComparator<SegmentsExperience> orderByComparator,
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

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SegmentsExperience segmentsExperience :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"segmentsExperience.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(segmentsExperience.uuid IS NULL OR segmentsExperience.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the segments experience where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchExperienceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByUUID_G(String uuid, long groupId)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByUUID_G(uuid, groupId);

		if (segmentsExperience == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchExperienceException(msg.toString());
		}

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the segments experience where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof SegmentsExperience) {
			SegmentsExperience segmentsExperience = (SegmentsExperience)result;

			if (!Objects.equals(uuid, segmentsExperience.getUuid()) ||
				(groupId != segmentsExperience.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<SegmentsExperience> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					SegmentsExperience segmentsExperience = list.get(0);

					result = segmentsExperience;

					cacheResult(segmentsExperience);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
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
			return (SegmentsExperience)result;
		}
	}

	/**
	 * Removes the segments experience where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the segments experience that was removed
	 */
	@Override
	public SegmentsExperience removeByUUID_G(String uuid, long groupId)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByUUID_G(uuid, groupId);

		return remove(segmentsExperience);
	}

	/**
	 * Returns the number of segments experiences where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"segmentsExperience.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(segmentsExperience.uuid IS NULL OR segmentsExperience.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"segmentsExperience.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the segments experiences where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if (!uuid.equals(segmentsExperience.getUuid()) ||
						(companyId != segmentsExperience.getCompanyId())) {

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

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByUuid_C_PrevAndNext(
			long segmentsExperienceId, String uuid, long companyId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		uuid = Objects.toString(uuid, "");

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, segmentsExperience, uuid, companyId, orderByComparator,
				true);

			array[1] = segmentsExperience;

			array[2] = getByUuid_C_PrevAndNext(
				session, segmentsExperience, uuid, companyId, orderByComparator,
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

	protected SegmentsExperience getByUuid_C_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, String uuid,
		long companyId, OrderByComparator<SegmentsExperience> orderByComparator,
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

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SegmentsExperience segmentsExperience :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"segmentsExperience.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(segmentsExperience.uuid IS NULL OR segmentsExperience.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"segmentsExperience.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the segments experiences where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
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

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if (groupId != segmentsExperience.getGroupId()) {
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

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByGroupId_First(
			long groupId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByGroupId_First(
			groupId, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByGroupId_First(
		long groupId, OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByGroupId_Last(
			long groupId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByGroupId_Last(
		long groupId, OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByGroupId_PrevAndNext(
			long segmentsExperienceId, long groupId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, segmentsExperience, groupId, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByGroupId_PrevAndNext(
				session, segmentsExperience, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience getByGroupId_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator,
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

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
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
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiences that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SegmentsExperience>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set of segments experiences that the user has permission to view where groupId = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] filterFindByGroupId_PrevAndNext(
			long segmentsExperienceId, long groupId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				segmentsExperienceId, groupId, orderByComparator);
		}

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, segmentsExperience, groupId, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, segmentsExperience, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience filterGetByGroupId_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SegmentsExperience segmentsExperience :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of segments experiences that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments experiences that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"segmentsExperience.groupId = ?";

	private FinderPath _finderPathWithPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathWithoutPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathCountBySegmentsEntryId;

	/**
	 * Returns all the segments experiences where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findBySegmentsEntryId(
		long segmentsEntryId) {

		return findBySegmentsEntryId(
			segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return findBySegmentsEntryId(segmentsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBySegmentsEntryId;
				finderArgs = new Object[] {segmentsEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySegmentsEntryId;
			finderArgs = new Object[] {
				segmentsEntryId, start, end, orderByComparator
			};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if (segmentsEntryId !=
							segmentsExperience.getSegmentsEntryId()) {

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

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findBySegmentsEntryId(
			segmentsEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countBySegmentsEntryId(segmentsEntryId);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findBySegmentsEntryId(
			segmentsEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findBySegmentsEntryId_PrevAndNext(
			long segmentsExperienceId, long segmentsEntryId,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getBySegmentsEntryId_PrevAndNext(
				session, segmentsExperience, segmentsEntryId, orderByComparator,
				true);

			array[1] = segmentsExperience;

			array[2] = getBySegmentsEntryId_PrevAndNext(
				session, segmentsExperience, segmentsEntryId, orderByComparator,
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

	protected SegmentsExperience getBySegmentsEntryId_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience,
		long segmentsEntryId,
		OrderByComparator<SegmentsExperience> orderByComparator,
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

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(segmentsEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	@Override
	public void removeBySegmentsEntryId(long segmentsEntryId) {
		for (SegmentsExperience segmentsExperience :
				findBySegmentsEntryId(
					segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countBySegmentsEntryId(long segmentsEntryId) {
		FinderPath finderPath = _finderPathCountBySegmentsEntryId;

		Object[] finderArgs = new Object[] {segmentsEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

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

	private static final String
		_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2 =
			"segmentsExperience.segmentsEntryId = ?";

	private FinderPath _finderPathFetchByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns the segments experience where groupId = &#63; and segmentsExperienceKey = &#63; or throws a <code>NoSuchExperienceException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceKey the segments experience key
	 * @return the matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_S(
			long groupId, String segmentsExperienceKey)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_S(
			groupId, segmentsExperienceKey);

		if (segmentsExperience == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", segmentsExperienceKey=");
			msg.append(segmentsExperienceKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchExperienceException(msg.toString());
		}

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience where groupId = &#63; and segmentsExperienceKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceKey the segments experience key
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_S(
		long groupId, String segmentsExperienceKey) {

		return fetchByG_S(groupId, segmentsExperienceKey, true);
	}

	/**
	 * Returns the segments experience where groupId = &#63; and segmentsExperienceKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceKey the segments experience key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_S(
		long groupId, String segmentsExperienceKey, boolean useFinderCache) {

		segmentsExperienceKey = Objects.toString(segmentsExperienceKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, segmentsExperienceKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_S, finderArgs, this);
		}

		if (result instanceof SegmentsExperience) {
			SegmentsExperience segmentsExperience = (SegmentsExperience)result;

			if ((groupId != segmentsExperience.getGroupId()) ||
				!Objects.equals(
					segmentsExperienceKey,
					segmentsExperience.getSegmentsExperienceKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindSegmentsExperienceKey = false;

			if (segmentsExperienceKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIENCEKEY_3);
			}
			else {
				bindSegmentsExperienceKey = true;

				query.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIENCEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindSegmentsExperienceKey) {
					qPos.add(segmentsExperienceKey);
				}

				List<SegmentsExperience> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_S, finderArgs, list);
					}
				}
				else {
					SegmentsExperience segmentsExperience = list.get(0);

					result = segmentsExperience;

					cacheResult(segmentsExperience);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByG_S, finderArgs);
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
			return (SegmentsExperience)result;
		}
	}

	/**
	 * Removes the segments experience where groupId = &#63; and segmentsExperienceKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceKey the segments experience key
	 * @return the segments experience that was removed
	 */
	@Override
	public SegmentsExperience removeByG_S(
			long groupId, String segmentsExperienceKey)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByG_S(
			groupId, segmentsExperienceKey);

		return remove(segmentsExperience);
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and segmentsExperienceKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceKey the segments experience key
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_S(long groupId, String segmentsExperienceKey) {
		segmentsExperienceKey = Objects.toString(segmentsExperienceKey, "");

		FinderPath finderPath = _finderPathCountByG_S;

		Object[] finderArgs = new Object[] {groupId, segmentsExperienceKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindSegmentsExperienceKey = false;

			if (segmentsExperienceKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIENCEKEY_3);
			}
			else {
				bindSegmentsExperienceKey = true;

				query.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIENCEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindSegmentsExperienceKey) {
					qPos.add(segmentsExperienceKey);
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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"segmentsExperience.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_SEGMENTSEXPERIENCEKEY_2 =
		"segmentsExperience.segmentsExperienceKey = ?";

	private static final String _FINDER_COLUMN_G_S_SEGMENTSEXPERIENCEKEY_3 =
		"(segmentsExperience.segmentsExperienceKey IS NULL OR segmentsExperience.segmentsExperienceKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C;
				finderArgs = new Object[] {groupId, classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId()) ||
						(classNameId != segmentsExperience.getClassNameId()) ||
						(classPK != segmentsExperience.getClassPK())) {

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

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByG_C_C(
			groupId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByG_C_C(
			groupId, classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByG_C_C_PrevAndNext(
			long segmentsExperienceId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByG_C_C_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByG_C_C_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
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

	protected SegmentsExperience getByG_C_C_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator,
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

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C(
		long groupId, long classNameId, long classPK) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C(
				groupId, classNameId, classPK, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			return (List<SegmentsExperience>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set of segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] filterFindByG_C_C_PrevAndNext(
			long segmentsExperienceId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_PrevAndNext(
				segmentsExperienceId, groupId, classNameId, classPK,
				orderByComparator);
		}

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = filterGetByG_C_C_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = filterGetByG_C_C_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
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

	protected SegmentsExperience filterGetByG_C_C_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (SegmentsExperience segmentsExperience :
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByG_C_C;

		Object[] finderArgs = new Object[] {groupId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	/**
	 * Returns the number of segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments experiences that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C(
		long groupId, long classNameId, long classPK) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C(groupId, classNameId, classPK);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"segmentsExperience.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"segmentsExperience.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"segmentsExperience.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_S_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_S_C_C;
	private FinderPath _finderPathCountByG_S_C_C;

	/**
	 * Returns all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK) {

		return findByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		int start, int end) {

		return findByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S_C_C;
				finderArgs = new Object[] {
					groupId, segmentsEntryId, classNameId, classPK
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S_C_C;
			finderArgs = new Object[] {
				groupId, segmentsEntryId, classNameId, classPK, start, end,
				orderByComparator
			};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId()) ||
						(segmentsEntryId !=
							segmentsExperience.getSegmentsEntryId()) ||
						(classNameId != segmentsExperience.getClassNameId()) ||
						(classPK != segmentsExperience.getClassPK())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSENTRYID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(segmentsEntryId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_S_C_C_First(
			long groupId, long segmentsEntryId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_S_C_C_First(
			groupId, segmentsEntryId, classNameId, classPK, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_S_C_C_First(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_S_C_C_Last(
			long groupId, long segmentsEntryId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_S_C_C_Last(
			groupId, segmentsEntryId, classNameId, classPK, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_S_C_C_Last(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByG_S_C_C_PrevAndNext(
			long segmentsExperienceId, long groupId, long segmentsEntryId,
			long classNameId, long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByG_S_C_C_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
				classNameId, classPK, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByG_S_C_C_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
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

	protected SegmentsExperience getByG_S_C_C_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long segmentsEntryId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(segmentsEntryId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK) {

		return filterFindByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		int start, int end) {

		return filterFindByG_S_C_C(
			groupId, segmentsEntryId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences that the user has permissions to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_C_C(
				groupId, segmentsEntryId, classNameId, classPK, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(segmentsEntryId);

			qPos.add(classNameId);

			qPos.add(classPK);

			return (List<SegmentsExperience>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set of segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] filterFindByG_S_C_C_PrevAndNext(
			long segmentsExperienceId, long groupId, long segmentsEntryId,
			long classNameId, long classPK,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_C_C_PrevAndNext(
				segmentsExperienceId, groupId, segmentsEntryId, classNameId,
				classPK, orderByComparator);
		}

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = filterGetByG_S_C_C_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
				classNameId, classPK, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = filterGetByG_S_C_C_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
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

	protected SegmentsExperience filterGetByG_S_C_C_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long segmentsEntryId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(segmentsEntryId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK) {

		for (SegmentsExperience segmentsExperience :
				findByG_S_C_C(
					groupId, segmentsEntryId, classNameId, classPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK) {

		FinderPath finderPath = _finderPathCountByG_S_C_C;

		Object[] finderArgs = new Object[] {
			groupId, segmentsEntryId, classNameId, classPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSENTRYID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(segmentsEntryId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	/**
	 * Returns the number of segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments experiences that the user has permission to view
	 */
	@Override
	public int filterCountByG_S_C_C(
		long groupId, long segmentsEntryId, long classNameId, long classPK) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S_C_C(
				groupId, segmentsEntryId, classNameId, classPK);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(segmentsEntryId);

			qPos.add(classNameId);

			qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_S_C_C_GROUPID_2 =
		"segmentsExperience.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_SEGMENTSENTRYID_2 =
		"segmentsExperience.segmentsEntryId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2 =
		"segmentsExperience.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_CLASSPK_2 =
		"segmentsExperience.classPK = ?";

	private FinderPath _finderPathFetchByG_C_C_P;
	private FinderPath _finderPathCountByG_C_C_P;

	/**
	 * Returns the segments experience where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority = &#63; or throws a <code>NoSuchExperienceException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_C_C_P(
			long groupId, long classNameId, long classPK, int priority)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_C_C_P(
			groupId, classNameId, classPK, priority);

		if (segmentsExperience == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", priority=");
			msg.append(priority);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchExperienceException(msg.toString());
		}

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_P(
		long groupId, long classNameId, long classPK, int priority) {

		return fetchByG_C_C_P(groupId, classNameId, classPK, priority, true);
	}

	/**
	 * Returns the segments experience where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_P(
		long groupId, long classNameId, long classPK, int priority,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, classNameId, classPK, priority};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_C_P, finderArgs, this);
		}

		if (result instanceof SegmentsExperience) {
			SegmentsExperience segmentsExperience = (SegmentsExperience)result;

			if ((groupId != segmentsExperience.getGroupId()) ||
				(classNameId != segmentsExperience.getClassNameId()) ||
				(classPK != segmentsExperience.getClassPK()) ||
				(priority != segmentsExperience.getPriority())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_P_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_P_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(priority);

				List<SegmentsExperience> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_C_C_P, finderArgs, list);
					}
				}
				else {
					SegmentsExperience segmentsExperience = list.get(0);

					result = segmentsExperience;

					cacheResult(segmentsExperience);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByG_C_C_P, finderArgs);
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
			return (SegmentsExperience)result;
		}
	}

	/**
	 * Removes the segments experience where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the segments experience that was removed
	 */
	@Override
	public SegmentsExperience removeByG_C_C_P(
			long groupId, long classNameId, long classPK, int priority)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByG_C_C_P(
			groupId, classNameId, classPK, priority);

		return remove(segmentsExperience);
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_C_C_P(
		long groupId, long classNameId, long classPK, int priority) {

		FinderPath finderPath = _finderPathCountByG_C_C_P;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, priority
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_P_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_P_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(priority);

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

	private static final String _FINDER_COLUMN_G_C_C_P_GROUPID_2 =
		"segmentsExperience.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_P_CLASSNAMEID_2 =
		"segmentsExperience.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_P_CLASSPK_2 =
		"segmentsExperience.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_P_PRIORITY_2 =
		"segmentsExperience.priority = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_GtP;
	private FinderPath _finderPathWithPaginationCountByG_C_C_GtP;

	/**
	 * Returns all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority) {

		return findByG_C_C_GtP(
			groupId, classNameId, classPK, priority, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority, int start,
		int end) {

		return findByG_C_C_GtP(
			groupId, classNameId, classPK, priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByG_C_C_GtP(
			groupId, classNameId, classPK, priority, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_C_C_GtP;
		finderArgs = new Object[] {
			groupId, classNameId, classPK, priority, start, end,
			orderByComparator
		};

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId()) ||
						(classNameId != segmentsExperience.getClassNameId()) ||
						(classPK != segmentsExperience.getClassPK()) ||
						(priority >= segmentsExperience.getPriority())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GTP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_GTP_PRIORITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(priority);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_C_C_GtP_First(
			long groupId, long classNameId, long classPK, int priority,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_C_C_GtP_First(
			groupId, classNameId, classPK, priority, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", priority>");
		msg.append(priority);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_GtP_First(
		long groupId, long classNameId, long classPK, int priority,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByG_C_C_GtP(
			groupId, classNameId, classPK, priority, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_C_C_GtP_Last(
			long groupId, long classNameId, long classPK, int priority,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_C_C_GtP_Last(
			groupId, classNameId, classPK, priority, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", priority>");
		msg.append(priority);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_GtP_Last(
		long groupId, long classNameId, long classPK, int priority,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByG_C_C_GtP(groupId, classNameId, classPK, priority);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByG_C_C_GtP(
			groupId, classNameId, classPK, priority, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByG_C_C_GtP_PrevAndNext(
			long segmentsExperienceId, long groupId, long classNameId,
			long classPK, int priority,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByG_C_C_GtP_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				priority, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByG_C_C_GtP_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				priority, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience getByG_C_C_GtP_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long classNameId, long classPK, int priority,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_PRIORITY_2);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(priority);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority) {

		return filterFindByG_C_C_GtP(
			groupId, classNameId, classPK, priority, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority, int start,
		int end) {

		return filterFindByG_C_C_GtP(
			groupId, classNameId, classPK, priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_GtP(
				groupId, classNameId, classPK, priority, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_PRIORITY_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(priority);

			return (List<SegmentsExperience>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set of segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] filterFindByG_C_C_GtP_PrevAndNext(
			long segmentsExperienceId, long groupId, long classNameId,
			long classPK, int priority,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_GtP_PrevAndNext(
				segmentsExperienceId, groupId, classNameId, classPK, priority,
				orderByComparator);
		}

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = filterGetByG_C_C_GtP_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				priority, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = filterGetByG_C_C_GtP_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				priority, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience filterGetByG_C_C_GtP_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long classNameId, long classPK, int priority,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_PRIORITY_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(priority);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 */
	@Override
	public void removeByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority) {

		for (SegmentsExperience segmentsExperience :
				findByG_C_C_GtP(
					groupId, classNameId, classPK, priority, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority) {

		FinderPath finderPath = _finderPathWithPaginationCountByG_C_C_GtP;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, priority
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GTP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_GTP_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(priority);

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

	/**
	 * Returns the number of segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and priority &gt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param priority the priority
	 * @return the number of matching segments experiences that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_GtP(
		long groupId, long classNameId, long classPK, int priority) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_GtP(groupId, classNameId, classPK, priority);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_GTP_PRIORITY_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(priority);

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

	private static final String _FINDER_COLUMN_G_C_C_GTP_GROUPID_2 =
		"segmentsExperience.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_GTP_CLASSNAMEID_2 =
		"segmentsExperience.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_GTP_CLASSPK_2 =
		"segmentsExperience.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_GTP_PRIORITY_2 =
		"segmentsExperience.priority > ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_A;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_A;
	private FinderPath _finderPathCountByG_C_C_A;

	/**
	 * Returns all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active) {

		return findByG_C_C_A(
			groupId, classNameId, classPK, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end) {

		return findByG_C_C_A(
			groupId, classNameId, classPK, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByG_C_C_A(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_A;
				finderArgs = new Object[] {
					groupId, classNameId, classPK, active
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C_A;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, active, start, end,
				orderByComparator
			};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId()) ||
						(classNameId != segmentsExperience.getClassNameId()) ||
						(classPK != segmentsExperience.getClassPK()) ||
						(active != segmentsExperience.isActive())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(active);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_C_C_A_First(
			long groupId, long classNameId, long classPK, boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_C_C_A_First(
			groupId, classNameId, classPK, active, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_A_First(
		long groupId, long classNameId, long classPK, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByG_C_C_A(
			groupId, classNameId, classPK, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_C_C_A_Last(
			long groupId, long classNameId, long classPK, boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_C_C_A_Last(
			groupId, classNameId, classPK, active, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_C_C_A_Last(
		long groupId, long classNameId, long classPK, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByG_C_C_A(groupId, classNameId, classPK, active);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByG_C_C_A(
			groupId, classNameId, classPK, active, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByG_C_C_A_PrevAndNext(
			long segmentsExperienceId, long groupId, long classNameId,
			long classPK, boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByG_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				active, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByG_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience getByG_C_C_A_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long classNameId, long classPK, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_A_ACTIVE_2);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active) {

		return filterFindByG_C_C_A(
			groupId, classNameId, classPK, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end) {

		return filterFindByG_C_C_A(
			groupId, classNameId, classPK, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_A(
				groupId, classNameId, classPK, active, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(active);

			return (List<SegmentsExperience>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set of segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] filterFindByG_C_C_A_PrevAndNext(
			long segmentsExperienceId, long groupId, long classNameId,
			long classPK, boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_A_PrevAndNext(
				segmentsExperienceId, groupId, classNameId, classPK, active,
				orderByComparator);
		}

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = filterGetByG_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				active, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = filterGetByG_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, classNameId, classPK,
				active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience filterGetByG_C_C_A_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long classNameId, long classPK, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 */
	@Override
	public void removeByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active) {

		for (SegmentsExperience segmentsExperience :
				findByG_C_C_A(
					groupId, classNameId, classPK, active, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active) {

		FinderPath finderPath = _finderPathCountByG_C_C_A;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, active
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(active);

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

	/**
	 * Returns the number of segments experiences that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the number of matching segments experiences that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_A(
		long groupId, long classNameId, long classPK, boolean active) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_A(groupId, classNameId, classPK, active);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_A_GROUPID_2 =
		"segmentsExperience.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_A_CLASSNAMEID_2 =
		"segmentsExperience.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_A_CLASSPK_2 =
		"segmentsExperience.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_A_ACTIVE_2 =
		"segmentsExperience.active = ?";

	private static final String _FINDER_COLUMN_G_C_C_A_ACTIVE_2_SQL =
		"segmentsExperience.active_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_S_C_C_A;
	private FinderPath _finderPathWithoutPaginationFindByG_S_C_C_A;
	private FinderPath _finderPathCountByG_S_C_C_A;
	private FinderPath _finderPathWithPaginationCountByG_S_C_C_A;

	/**
	 * Returns all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active) {

		return findByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active, int start, int end) {

		return findByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S_C_C_A;
				finderArgs = new Object[] {
					groupId, segmentsEntryId, classNameId, classPK, active
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S_C_C_A;
			finderArgs = new Object[] {
				groupId, segmentsEntryId, classNameId, classPK, active, start,
				end, orderByComparator
			};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId()) ||
						(segmentsEntryId !=
							segmentsExperience.getSegmentsEntryId()) ||
						(classNameId != segmentsExperience.getClassNameId()) ||
						(classPK != segmentsExperience.getClassPK()) ||
						(active != segmentsExperience.isActive())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(segmentsEntryId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(active);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_S_C_C_A_First(
			long groupId, long segmentsEntryId, long classNameId, long classPK,
			boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_S_C_C_A_First(
			groupId, segmentsEntryId, classNameId, classPK, active,
			orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_S_C_C_A_First(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		List<SegmentsExperience> list = findByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_S_C_C_A_Last(
			long groupId, long segmentsEntryId, long classNameId, long classPK,
			boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByG_S_C_C_A_Last(
			groupId, segmentsEntryId, classNameId, classPK, active,
			orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_S_C_C_A_Last(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		int count = countByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] findByG_S_C_C_A_PrevAndNext(
			long segmentsExperienceId, long groupId, long segmentsEntryId,
			long classNameId, long classPK, boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByG_S_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
				classNameId, classPK, active, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByG_S_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
				classNameId, classPK, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience getByG_S_C_C_A_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long segmentsEntryId, long classNameId, long classPK, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2);

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
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(segmentsEntryId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active) {

		return filterFindByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active, int start, int end) {

		return filterFindByG_S_C_C_A(
			groupId, segmentsEntryId, classNameId, classPK, active, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the segments experiences that the user has permissions to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_C_C_A(
				groupId, segmentsEntryId, classNameId, classPK, active, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(segmentsEntryId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(active);

			return (List<SegmentsExperience>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the segments experiences before and after the current segments experience in the ordered set of segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param segmentsExperienceId the primary key of the current segments experience
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience[] filterFindByG_S_C_C_A_PrevAndNext(
			long segmentsExperienceId, long groupId, long segmentsEntryId,
			long classNameId, long classPK, boolean active,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_C_C_A_PrevAndNext(
				segmentsExperienceId, groupId, segmentsEntryId, classNameId,
				classPK, active, orderByComparator);
		}

		SegmentsExperience segmentsExperience = findByPrimaryKey(
			segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = filterGetByG_S_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
				classNameId, classPK, active, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = filterGetByG_S_C_C_A_PrevAndNext(
				session, segmentsExperience, groupId, segmentsEntryId,
				classNameId, classPK, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience filterGetByG_S_C_C_A_PrevAndNext(
		Session session, SegmentsExperience segmentsExperience, long groupId,
		long segmentsEntryId, long classNameId, long classPK, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(segmentsEntryId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperience)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active) {

		return filterFindByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId, classPK, active,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end) {

		return filterFindByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId, classPK, active, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences that the user has permission to view
	 */
	@Override
	public List<SegmentsExperience> filterFindByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_C_C_A(
				groupId, segmentsEntryIds, classNameId, classPK, active, start,
				end, orderByComparator);
		}

		if (segmentsEntryIds == null) {
			segmentsEntryIds = new long[0];
		}
		else if (segmentsEntryIds.length > 1) {
			segmentsEntryIds = ArrayUtil.sortedUnique(segmentsEntryIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

		if (segmentsEntryIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_7);

			query.append(StringUtil.merge(segmentsEntryIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2_SQL);

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SegmentsExperienceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SegmentsExperienceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(active);

			return (List<SegmentsExperience>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the segments experiences where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active) {

		return findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId, classPK, active,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end) {

		return findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId, classPK, active, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId, classPK, active, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean useFinderCache) {

		if (segmentsEntryIds == null) {
			segmentsEntryIds = new long[0];
		}
		else if (segmentsEntryIds.length > 1) {
			segmentsEntryIds = ArrayUtil.sortedUnique(segmentsEntryIds);
		}

		if (segmentsEntryIds.length == 1) {
			return findByG_S_C_C_A(
				groupId, segmentsEntryIds[0], classNameId, classPK, active,
				start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(segmentsEntryIds), classNameId,
					classPK, active
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(segmentsEntryIds), classNameId,
				classPK, active, start, end, orderByComparator
			};
		}

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				_finderPathWithPaginationFindByG_S_C_C_A, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId()) ||
						!ArrayUtil.contains(
							segmentsEntryIds,
							segmentsExperience.getSegmentsEntryId()) ||
						(classNameId != segmentsExperience.getClassNameId()) ||
						(classPK != segmentsExperience.getClassPK()) ||
						(active != segmentsExperience.isActive())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

			if (segmentsEntryIds.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_7);

				query.append(StringUtil.merge(segmentsEntryIds));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(active);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_S_C_C_A, finderArgs,
						list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByG_S_C_C_A, finderArgs);
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
	 * Removes all the segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 */
	@Override
	public void removeByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active) {

		for (SegmentsExperience segmentsExperience :
				findByG_S_C_C_A(
					groupId, segmentsEntryId, classNameId, classPK, active,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active) {

		FinderPath finderPath = _finderPathCountByG_S_C_C_A;

		Object[] finderArgs = new Object[] {
			groupId, segmentsEntryId, classNameId, classPK, active
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(segmentsEntryId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(active);

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

	/**
	 * Returns the number of segments experiences where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active) {

		if (segmentsEntryIds == null) {
			segmentsEntryIds = new long[0];
		}
		else if (segmentsEntryIds.length > 1) {
			segmentsEntryIds = ArrayUtil.sortedUnique(segmentsEntryIds);
		}

		Object[] finderArgs = new Object[] {
			groupId, StringUtil.merge(segmentsEntryIds), classNameId, classPK,
			active
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_S_C_C_A, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

			if (segmentsEntryIds.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_7);

				query.append(StringUtil.merge(segmentsEntryIds));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(active);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_S_C_C_A, finderArgs,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByG_S_C_C_A, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the number of matching segments experiences that the user has permission to view
	 */
	@Override
	public int filterCountByG_S_C_C_A(
		long groupId, long segmentsEntryId, long classNameId, long classPK,
		boolean active) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S_C_C_A(
				groupId, segmentsEntryId, classNameId, classPK, active);
		}

		StringBundler query = new StringBundler(6);

		query.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(segmentsEntryId);

			qPos.add(classNameId);

			qPos.add(classPK);

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

	/**
	 * Returns the number of segments experiences that the user has permission to view where groupId = &#63; and segmentsEntryId = any &#63; and classNameId = &#63; and classPK = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryIds the segments entry IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param active the active
	 * @return the number of matching segments experiences that the user has permission to view
	 */
	@Override
	public int filterCountByG_S_C_C_A(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S_C_C_A(
				groupId, segmentsEntryIds, classNameId, classPK, active);
		}

		if (segmentsEntryIds == null) {
			segmentsEntryIds = new long[0];
		}
		else if (segmentsEntryIds.length > 1) {
			segmentsEntryIds = ArrayUtil.sortedUnique(segmentsEntryIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_S_C_C_A_GROUPID_2);

		if (segmentsEntryIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_7);

			query.append(StringUtil.merge(segmentsEntryIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_S_C_C_A_ACTIVE_2_SQL);

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SegmentsExperience.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_S_C_C_A_GROUPID_2 =
		"segmentsExperience.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_2 =
		"segmentsExperience.segmentsEntryId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_A_SEGMENTSENTRYID_7 =
		"segmentsExperience.segmentsEntryId IN (";

	private static final String _FINDER_COLUMN_G_S_C_C_A_CLASSNAMEID_2 =
		"segmentsExperience.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_A_CLASSPK_2 =
		"segmentsExperience.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_A_ACTIVE_2 =
		"segmentsExperience.active = ?";

	private static final String _FINDER_COLUMN_G_S_C_C_A_ACTIVE_2_SQL =
		"segmentsExperience.active_ = ?";

	public SegmentsExperiencePersistenceImpl() {
		setModelClass(SegmentsExperience.class);

		setModelImplClass(SegmentsExperienceImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the segments experience in the entity cache if it is enabled.
	 *
	 * @param segmentsExperience the segments experience
	 */
	@Override
	public void cacheResult(SegmentsExperience segmentsExperience) {
		entityCache.putResult(
			entityCacheEnabled, SegmentsExperienceImpl.class,
			segmentsExperience.getPrimaryKey(), segmentsExperience);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				segmentsExperience.getUuid(), segmentsExperience.getGroupId()
			},
			segmentsExperience);

		finderCache.putResult(
			_finderPathFetchByG_S,
			new Object[] {
				segmentsExperience.getGroupId(),
				segmentsExperience.getSegmentsExperienceKey()
			},
			segmentsExperience);

		finderCache.putResult(
			_finderPathFetchByG_C_C_P,
			new Object[] {
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				segmentsExperience.getPriority()
			},
			segmentsExperience);

		segmentsExperience.resetOriginalValues();
	}

	/**
	 * Caches the segments experiences in the entity cache if it is enabled.
	 *
	 * @param segmentsExperiences the segments experiences
	 */
	@Override
	public void cacheResult(List<SegmentsExperience> segmentsExperiences) {
		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			if (entityCache.getResult(
					entityCacheEnabled, SegmentsExperienceImpl.class,
					segmentsExperience.getPrimaryKey()) == null) {

				cacheResult(segmentsExperience);
			}
			else {
				segmentsExperience.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all segments experiences.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SegmentsExperienceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the segments experience.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SegmentsExperience segmentsExperience) {
		entityCache.removeResult(
			entityCacheEnabled, SegmentsExperienceImpl.class,
			segmentsExperience.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SegmentsExperienceModelImpl)segmentsExperience, true);
	}

	@Override
	public void clearCache(List<SegmentsExperience> segmentsExperiences) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			entityCache.removeResult(
				entityCacheEnabled, SegmentsExperienceImpl.class,
				segmentsExperience.getPrimaryKey());

			clearUniqueFindersCache(
				(SegmentsExperienceModelImpl)segmentsExperience, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SegmentsExperienceModelImpl segmentsExperienceModelImpl) {

		Object[] args = new Object[] {
			segmentsExperienceModelImpl.getUuid(),
			segmentsExperienceModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, segmentsExperienceModelImpl, false);

		args = new Object[] {
			segmentsExperienceModelImpl.getGroupId(),
			segmentsExperienceModelImpl.getSegmentsExperienceKey()
		};

		finderCache.putResult(
			_finderPathCountByG_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_S, args, segmentsExperienceModelImpl, false);

		args = new Object[] {
			segmentsExperienceModelImpl.getGroupId(),
			segmentsExperienceModelImpl.getClassNameId(),
			segmentsExperienceModelImpl.getClassPK(),
			segmentsExperienceModelImpl.getPriority()
		};

		finderCache.putResult(
			_finderPathCountByG_C_C_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_C_C_P, args, segmentsExperienceModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SegmentsExperienceModelImpl segmentsExperienceModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsExperienceModelImpl.getUuid(),
				segmentsExperienceModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((segmentsExperienceModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsExperienceModelImpl.getOriginalUuid(),
				segmentsExperienceModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsExperienceModelImpl.getGroupId(),
				segmentsExperienceModelImpl.getSegmentsExperienceKey()
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(_finderPathFetchByG_S, args);
		}

		if ((segmentsExperienceModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsExperienceModelImpl.getOriginalGroupId(),
				segmentsExperienceModelImpl.getOriginalSegmentsExperienceKey()
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(_finderPathFetchByG_S, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsExperienceModelImpl.getGroupId(),
				segmentsExperienceModelImpl.getClassNameId(),
				segmentsExperienceModelImpl.getClassPK(),
				segmentsExperienceModelImpl.getPriority()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_P, args);
			finderCache.removeResult(_finderPathFetchByG_C_C_P, args);
		}

		if ((segmentsExperienceModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_C_C_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsExperienceModelImpl.getOriginalGroupId(),
				segmentsExperienceModelImpl.getOriginalClassNameId(),
				segmentsExperienceModelImpl.getOriginalClassPK(),
				segmentsExperienceModelImpl.getOriginalPriority()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_P, args);
			finderCache.removeResult(_finderPathFetchByG_C_C_P, args);
		}
	}

	/**
	 * Creates a new segments experience with the primary key. Does not add the segments experience to the database.
	 *
	 * @param segmentsExperienceId the primary key for the new segments experience
	 * @return the new segments experience
	 */
	@Override
	public SegmentsExperience create(long segmentsExperienceId) {
		SegmentsExperience segmentsExperience = new SegmentsExperienceImpl();

		segmentsExperience.setNew(true);
		segmentsExperience.setPrimaryKey(segmentsExperienceId);

		String uuid = PortalUUIDUtil.generate();

		segmentsExperience.setUuid(uuid);

		segmentsExperience.setCompanyId(CompanyThreadLocal.getCompanyId());

		return segmentsExperience;
	}

	/**
	 * Removes the segments experience with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience that was removed
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience remove(long segmentsExperienceId)
		throws NoSuchExperienceException {

		return remove((Serializable)segmentsExperienceId);
	}

	/**
	 * Removes the segments experience with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the segments experience
	 * @return the segments experience that was removed
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience remove(Serializable primaryKey)
		throws NoSuchExperienceException {

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience segmentsExperience =
				(SegmentsExperience)session.get(
					SegmentsExperienceImpl.class, primaryKey);

			if (segmentsExperience == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchExperienceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(segmentsExperience);
		}
		catch (NoSuchExperienceException nsee) {
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
	protected SegmentsExperience removeImpl(
		SegmentsExperience segmentsExperience) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsExperience)) {
				segmentsExperience = (SegmentsExperience)session.get(
					SegmentsExperienceImpl.class,
					segmentsExperience.getPrimaryKeyObj());
			}

			if (segmentsExperience != null) {
				session.delete(segmentsExperience);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (segmentsExperience != null) {
			clearCache(segmentsExperience);
		}

		return segmentsExperience;
	}

	@Override
	public SegmentsExperience updateImpl(
		SegmentsExperience segmentsExperience) {

		boolean isNew = segmentsExperience.isNew();

		if (!(segmentsExperience instanceof SegmentsExperienceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsExperience.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					segmentsExperience);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsExperience proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsExperience implementation " +
					segmentsExperience.getClass());
		}

		SegmentsExperienceModelImpl segmentsExperienceModelImpl =
			(SegmentsExperienceModelImpl)segmentsExperience;

		if (Validator.isNull(segmentsExperience.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			segmentsExperience.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (segmentsExperience.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsExperience.setCreateDate(now);
			}
			else {
				segmentsExperience.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!segmentsExperienceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsExperience.setModifiedDate(now);
			}
			else {
				segmentsExperience.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (segmentsExperience.isNew()) {
				session.save(segmentsExperience);

				segmentsExperience.setNew(false);
			}
			else {
				segmentsExperience = (SegmentsExperience)session.merge(
					segmentsExperience);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				segmentsExperienceModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				segmentsExperienceModelImpl.getUuid(),
				segmentsExperienceModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {segmentsExperienceModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				segmentsExperienceModelImpl.getSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountBySegmentsEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySegmentsEntryId, args);

			args = new Object[] {
				segmentsExperienceModelImpl.getGroupId(),
				segmentsExperienceModelImpl.getClassNameId(),
				segmentsExperienceModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_C_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C, args);

			args = new Object[] {
				segmentsExperienceModelImpl.getGroupId(),
				segmentsExperienceModelImpl.getSegmentsEntryId(),
				segmentsExperienceModelImpl.getClassNameId(),
				segmentsExperienceModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_S_C_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_S_C_C, args);

			args = new Object[] {
				segmentsExperienceModelImpl.getGroupId(),
				segmentsExperienceModelImpl.getClassNameId(),
				segmentsExperienceModelImpl.getClassPK(),
				segmentsExperienceModelImpl.isActive()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_A, args);

			args = new Object[] {
				segmentsExperienceModelImpl.getGroupId(),
				segmentsExperienceModelImpl.getSegmentsEntryId(),
				segmentsExperienceModelImpl.getClassNameId(),
				segmentsExperienceModelImpl.getClassPK(),
				segmentsExperienceModelImpl.isActive()
			};

			finderCache.removeResult(_finderPathCountByG_S_C_C_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_S_C_C_A, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {segmentsExperienceModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalUuid(),
					segmentsExperienceModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					segmentsExperienceModelImpl.getUuid(),
					segmentsExperienceModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {segmentsExperienceModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySegmentsEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);

				args = new Object[] {
					segmentsExperienceModelImpl.getSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalGroupId(),
					segmentsExperienceModelImpl.getOriginalClassNameId(),
					segmentsExperienceModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);

				args = new Object[] {
					segmentsExperienceModelImpl.getGroupId(),
					segmentsExperienceModelImpl.getClassNameId(),
					segmentsExperienceModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S_C_C.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalGroupId(),
					segmentsExperienceModelImpl.getOriginalSegmentsEntryId(),
					segmentsExperienceModelImpl.getOriginalClassNameId(),
					segmentsExperienceModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_S_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_C_C, args);

				args = new Object[] {
					segmentsExperienceModelImpl.getGroupId(),
					segmentsExperienceModelImpl.getSegmentsEntryId(),
					segmentsExperienceModelImpl.getClassNameId(),
					segmentsExperienceModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_S_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_C_C, args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_A.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalGroupId(),
					segmentsExperienceModelImpl.getOriginalClassNameId(),
					segmentsExperienceModelImpl.getOriginalClassPK(),
					segmentsExperienceModelImpl.getOriginalActive()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_A, args);

				args = new Object[] {
					segmentsExperienceModelImpl.getGroupId(),
					segmentsExperienceModelImpl.getClassNameId(),
					segmentsExperienceModelImpl.getClassPK(),
					segmentsExperienceModelImpl.isActive()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_A, args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S_C_C_A.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalGroupId(),
					segmentsExperienceModelImpl.getOriginalSegmentsEntryId(),
					segmentsExperienceModelImpl.getOriginalClassNameId(),
					segmentsExperienceModelImpl.getOriginalClassPK(),
					segmentsExperienceModelImpl.getOriginalActive()
				};

				finderCache.removeResult(_finderPathCountByG_S_C_C_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_C_C_A, args);

				args = new Object[] {
					segmentsExperienceModelImpl.getGroupId(),
					segmentsExperienceModelImpl.getSegmentsEntryId(),
					segmentsExperienceModelImpl.getClassNameId(),
					segmentsExperienceModelImpl.getClassPK(),
					segmentsExperienceModelImpl.isActive()
				};

				finderCache.removeResult(_finderPathCountByG_S_C_C_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_C_C_A, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, SegmentsExperienceImpl.class,
			segmentsExperience.getPrimaryKey(), segmentsExperience, false);

		clearUniqueFindersCache(segmentsExperienceModelImpl, false);
		cacheUniqueFindersCache(segmentsExperienceModelImpl);

		segmentsExperience.resetOriginalValues();

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments experience
	 * @return the segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience findByPrimaryKey(Serializable primaryKey)
		throws NoSuchExperienceException {

		SegmentsExperience segmentsExperience = fetchByPrimaryKey(primaryKey);

		if (segmentsExperience == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchExperienceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience with the primary key or throws a <code>NoSuchExperienceException</code> if it could not be found.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience findByPrimaryKey(long segmentsExperienceId)
		throws NoSuchExperienceException {

		return findByPrimaryKey((Serializable)segmentsExperienceId);
	}

	/**
	 * Returns the segments experience with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience, or <code>null</code> if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience fetchByPrimaryKey(long segmentsExperienceId) {
		return fetchByPrimaryKey((Serializable)segmentsExperienceId);
	}

	/**
	 * Returns all the segments experiences.
	 *
	 * @return the segments experiences
	 */
	@Override
	public List<SegmentsExperience> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of segments experiences
	 */
	@Override
	public List<SegmentsExperience> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments experiences
	 */
	@Override
	public List<SegmentsExperience> findAll(
		int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments experiences
	 */
	@Override
	public List<SegmentsExperience> findAll(
		int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
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

		List<SegmentsExperience> list = null;

		if (useFinderCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SEGMENTSEXPERIENCE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SEGMENTSEXPERIENCE;

				sql = sql.concat(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<SegmentsExperience>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Removes all the segments experiences from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SegmentsExperience segmentsExperience : findAll()) {
			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences.
	 *
	 * @return the number of segments experiences
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SEGMENTSEXPERIENCE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "segmentsExperienceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEGMENTSEXPERIENCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SegmentsExperienceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the segments experience persistence.
	 */
	@Activate
	public void activate() {
		SegmentsExperienceModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		SegmentsExperienceModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			SegmentsExperienceModelImpl.UUID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			SegmentsExperienceModelImpl.UUID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			SegmentsExperienceModelImpl.UUID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.COMPANYID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindBySegmentsEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySegmentsEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySegmentsEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySegmentsEntryId",
			new String[] {Long.class.getName()},
			SegmentsExperienceModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountBySegmentsEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySegmentsEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByG_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_S",
			new String[] {Long.class.getName(), String.class.getName()},
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.SEGMENTSEXPERIENCEKEY_COLUMN_BITMASK);

		_finderPathCountByG_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSPK_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByG_S_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSPK_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_S_C_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			});

		_finderPathFetchByG_C_C_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSPK_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_C_C_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_GtP = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_GtP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_C_C_GtP = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_C_GtP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSPK_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.ACTIVE_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_C_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_S_C_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S_C_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S_C_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SegmentsExperienceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_C_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.CLASSPK_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.ACTIVE_COLUMN_BITMASK |
			SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_S_C_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S_C_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationCountByG_S_C_C_A = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_S_C_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SegmentsExperienceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.segments.model.SegmentsExperience"),
			true);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SEGMENTSEXPERIENCE =
		"SELECT segmentsExperience FROM SegmentsExperience segmentsExperience";

	private static final String _SQL_SELECT_SEGMENTSEXPERIENCE_WHERE =
		"SELECT segmentsExperience FROM SegmentsExperience segmentsExperience WHERE ";

	private static final String _SQL_COUNT_SEGMENTSEXPERIENCE =
		"SELECT COUNT(segmentsExperience) FROM SegmentsExperience segmentsExperience";

	private static final String _SQL_COUNT_SEGMENTSEXPERIENCE_WHERE =
		"SELECT COUNT(segmentsExperience) FROM SegmentsExperience segmentsExperience WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"segmentsExperience.segmentsExperienceId";

	private static final String _FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE =
		"SELECT DISTINCT {segmentsExperience.*} FROM SegmentsExperience segmentsExperience WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {SegmentsExperience.*} FROM (SELECT DISTINCT segmentsExperience.segmentsExperienceId FROM SegmentsExperience segmentsExperience WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSEXPERIENCE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN SegmentsExperience ON TEMP_TABLE.segmentsExperienceId = SegmentsExperience.segmentsExperienceId";

	private static final String _FILTER_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE =
		"SELECT COUNT(DISTINCT segmentsExperience.segmentsExperienceId) AS COUNT_VALUE FROM SegmentsExperience segmentsExperience WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "segmentsExperience";

	private static final String _FILTER_ENTITY_TABLE = "SegmentsExperience";

	private static final String _ORDER_BY_ENTITY_ALIAS = "segmentsExperience.";

	private static final String _ORDER_BY_ENTITY_TABLE = "SegmentsExperience.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SegmentsExperience exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SegmentsExperience exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperiencePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active"});

	static {
		try {
			Class.forName(SegmentsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}