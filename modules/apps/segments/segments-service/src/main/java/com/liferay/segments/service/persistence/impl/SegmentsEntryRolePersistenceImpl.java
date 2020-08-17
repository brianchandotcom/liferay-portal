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
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.segments.exception.NoSuchEntryRoleException;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.model.SegmentsEntryRoleTable;
import com.liferay.segments.model.impl.SegmentsEntryRoleImpl;
import com.liferay.segments.model.impl.SegmentsEntryRoleModelImpl;
import com.liferay.segments.service.persistence.SegmentsEntryRolePersistence;
import com.liferay.segments.service.persistence.impl.constants.SegmentsPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the segments entry role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @generated
 */
@Component(service = SegmentsEntryRolePersistence.class)
public class SegmentsEntryRolePersistenceImpl
	extends BasePersistenceImpl<SegmentsEntryRole>
	implements SegmentsEntryRolePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SegmentsEntryRoleUtil</code> to access the segments entry role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SegmentsEntryRoleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathWithoutPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathCountBySegmentsEntryId;

	/**
	 * Returns all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findBySegmentsEntryId(long segmentsEntryId) {
		return findBySegmentsEntryId(
			segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return findBySegmentsEntryId(segmentsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindBySegmentsEntryId;
				finderArgs = new Object[] {segmentsEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindBySegmentsEntryId;
			finderArgs = new Object[] {
				segmentsEntryId, start, end, orderByComparator
			};
		}

		List<SegmentsEntryRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntryRole>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntryRole segmentsEntryRole : list) {
					if (segmentsEntryId !=
							segmentsEntryRole.getSegmentsEntryId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_SEGMENTSENTRYROLE_WHERE);

			sb.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsEntryId);

				list = (List<SegmentsEntryRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);

		if (segmentsEntryRole != null) {
			return segmentsEntryRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsEntryId=");
		sb.append(segmentsEntryId);

		sb.append("}");

		throw new NoSuchEntryRoleException(sb.toString());
	}

	/**
	 * Returns the first segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		List<SegmentsEntryRole> list = findBySegmentsEntryId(
			segmentsEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = fetchBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);

		if (segmentsEntryRole != null) {
			return segmentsEntryRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsEntryId=");
		sb.append(segmentsEntryId);

		sb.append("}");

		throw new NoSuchEntryRoleException(sb.toString());
	}

	/**
	 * Returns the last segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		int count = countBySegmentsEntryId(segmentsEntryId);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntryRole> list = findBySegmentsEntryId(
			segmentsEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entry roles before and after the current segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryRoleId the primary key of the current segments entry role
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole[] findBySegmentsEntryId_PrevAndNext(
			long segmentsEntryRoleId, long segmentsEntryId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = findByPrimaryKey(
			segmentsEntryRoleId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntryRole[] array = new SegmentsEntryRoleImpl[3];

			array[0] = getBySegmentsEntryId_PrevAndNext(
				session, segmentsEntryRole, segmentsEntryId, orderByComparator,
				true);

			array[1] = segmentsEntryRole;

			array[2] = getBySegmentsEntryId_PrevAndNext(
				session, segmentsEntryRole, segmentsEntryId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntryRole getBySegmentsEntryId_PrevAndNext(
		Session session, SegmentsEntryRole segmentsEntryRole,
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRYROLE_WHERE);

		sb.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SegmentsEntryRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(segmentsEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntryRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntryRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entry roles where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	@Override
	public void removeBySegmentsEntryId(long segmentsEntryId) {
		for (SegmentsEntryRole segmentsEntryRole :
				findBySegmentsEntryId(
					segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsEntryRole);
		}
	}

	/**
	 * Returns the number of segments entry roles where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entry roles
	 */
	@Override
	public int countBySegmentsEntryId(long segmentsEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountBySegmentsEntryId;

			finderArgs = new Object[] {segmentsEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SEGMENTSENTRYROLE_WHERE);

			sb.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsEntryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2 =
			"segmentsEntryRole.segmentsEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByRoleId;
	private FinderPath _finderPathWithoutPaginationFindByRoleId;
	private FinderPath _finderPathCountByRoleId;

	/**
	 * Returns all the segments entry roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findByRoleId(long roleId) {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end) {

		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return findByRoleId(roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByRoleId;
				finderArgs = new Object[] {roleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByRoleId;
			finderArgs = new Object[] {roleId, start, end, orderByComparator};
		}

		List<SegmentsEntryRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntryRole>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntryRole segmentsEntryRole : list) {
					if (roleId != segmentsEntryRole.getRoleId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_SEGMENTSENTRYROLE_WHERE);

			sb.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(roleId);

				list = (List<SegmentsEntryRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole findByRoleId_First(
			long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = fetchByRoleId_First(
			roleId, orderByComparator);

		if (segmentsEntryRole != null) {
			return segmentsEntryRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchEntryRoleException(sb.toString());
	}

	/**
	 * Returns the first segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole fetchByRoleId_First(
		long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator) {

		List<SegmentsEntryRole> list = findByRoleId(
			roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole findByRoleId_Last(
			long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = fetchByRoleId_Last(
			roleId, orderByComparator);

		if (segmentsEntryRole != null) {
			return segmentsEntryRole;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchEntryRoleException(sb.toString());
	}

	/**
	 * Returns the last segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole fetchByRoleId_Last(
		long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator) {

		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntryRole> list = findByRoleId(
			roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entry roles before and after the current segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param segmentsEntryRoleId the primary key of the current segments entry role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole[] findByRoleId_PrevAndNext(
			long segmentsEntryRoleId, long roleId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = findByPrimaryKey(
			segmentsEntryRoleId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntryRole[] array = new SegmentsEntryRoleImpl[3];

			array[0] = getByRoleId_PrevAndNext(
				session, segmentsEntryRole, roleId, orderByComparator, true);

			array[1] = segmentsEntryRole;

			array[2] = getByRoleId_PrevAndNext(
				session, segmentsEntryRole, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntryRole getByRoleId_PrevAndNext(
		Session session, SegmentsEntryRole segmentsEntryRole, long roleId,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRYROLE_WHERE);

		sb.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SegmentsEntryRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntryRole)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntryRole> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entry roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	@Override
	public void removeByRoleId(long roleId) {
		for (SegmentsEntryRole segmentsEntryRole :
				findByRoleId(
					roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsEntryRole);
		}
	}

	/**
	 * Returns the number of segments entry roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching segments entry roles
	 */
	@Override
	public int countByRoleId(long roleId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByRoleId;

			finderArgs = new Object[] {roleId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SEGMENTSENTRYROLE_WHERE);

			sb.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(roleId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 =
		"segmentsEntryRole.roleId = ?";

	private FinderPath _finderPathFetchByS_R;
	private FinderPath _finderPathCountByS_R;

	/**
	 * Returns the segments entry role where segmentsEntryId = &#63; and roleId = &#63; or throws a <code>NoSuchEntryRoleException</code> if it could not be found.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param roleId the role ID
	 * @return the matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole findByS_R(long segmentsEntryId, long roleId)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = fetchByS_R(
			segmentsEntryId, roleId);

		if (segmentsEntryRole == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("segmentsEntryId=");
			sb.append(segmentsEntryId);

			sb.append(", roleId=");
			sb.append(roleId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryRoleException(sb.toString());
		}

		return segmentsEntryRole;
	}

	/**
	 * Returns the segments entry role where segmentsEntryId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param roleId the role ID
	 * @return the matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole fetchByS_R(long segmentsEntryId, long roleId) {
		return fetchByS_R(segmentsEntryId, roleId, true);
	}

	/**
	 * Returns the segments entry role where segmentsEntryId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param roleId the role ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	@Override
	public SegmentsEntryRole fetchByS_R(
		long segmentsEntryId, long roleId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {segmentsEntryId, roleId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByS_R, finderArgs, this);
		}

		if (result instanceof SegmentsEntryRole) {
			SegmentsEntryRole segmentsEntryRole = (SegmentsEntryRole)result;

			if ((segmentsEntryId != segmentsEntryRole.getSegmentsEntryId()) ||
				(roleId != segmentsEntryRole.getRoleId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SEGMENTSENTRYROLE_WHERE);

			sb.append(_FINDER_COLUMN_S_R_SEGMENTSENTRYID_2);

			sb.append(_FINDER_COLUMN_S_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsEntryId);

				queryPos.add(roleId);

				List<SegmentsEntryRole> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByS_R, finderArgs, list);
					}
				}
				else {
					SegmentsEntryRole segmentsEntryRole = list.get(0);

					result = segmentsEntryRole;

					cacheResult(segmentsEntryRole);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SegmentsEntryRole)result;
		}
	}

	/**
	 * Removes the segments entry role where segmentsEntryId = &#63; and roleId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param roleId the role ID
	 * @return the segments entry role that was removed
	 */
	@Override
	public SegmentsEntryRole removeByS_R(long segmentsEntryId, long roleId)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = findByS_R(
			segmentsEntryId, roleId);

		return remove(segmentsEntryRole);
	}

	/**
	 * Returns the number of segments entry roles where segmentsEntryId = &#63; and roleId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param roleId the role ID
	 * @return the number of matching segments entry roles
	 */
	@Override
	public int countByS_R(long segmentsEntryId, long roleId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByS_R;

			finderArgs = new Object[] {segmentsEntryId, roleId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SEGMENTSENTRYROLE_WHERE);

			sb.append(_FINDER_COLUMN_S_R_SEGMENTSENTRYID_2);

			sb.append(_FINDER_COLUMN_S_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsEntryId);

				queryPos.add(roleId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_S_R_SEGMENTSENTRYID_2 =
		"segmentsEntryRole.segmentsEntryId = ? AND ";

	private static final String _FINDER_COLUMN_S_R_ROLEID_2 =
		"segmentsEntryRole.roleId = ?";

	public SegmentsEntryRolePersistenceImpl() {
		setModelClass(SegmentsEntryRole.class);

		setModelImplClass(SegmentsEntryRoleImpl.class);
		setModelPKClass(long.class);

		setTable(SegmentsEntryRoleTable.INSTANCE);
	}

	/**
	 * Caches the segments entry role in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRole the segments entry role
	 */
	@Override
	public void cacheResult(SegmentsEntryRole segmentsEntryRole) {
		if (segmentsEntryRole.getCtCollectionId() != 0) {
			segmentsEntryRole.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			SegmentsEntryRoleImpl.class, segmentsEntryRole.getPrimaryKey(),
			segmentsEntryRole);

		finderCache.putResult(
			_finderPathFetchByS_R,
			new Object[] {
				segmentsEntryRole.getSegmentsEntryId(),
				segmentsEntryRole.getRoleId()
			},
			segmentsEntryRole);

		segmentsEntryRole.resetOriginalValues();
	}

	/**
	 * Caches the segments entry roles in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRoles the segments entry roles
	 */
	@Override
	public void cacheResult(List<SegmentsEntryRole> segmentsEntryRoles) {
		for (SegmentsEntryRole segmentsEntryRole : segmentsEntryRoles) {
			if (segmentsEntryRole.getCtCollectionId() != 0) {
				segmentsEntryRole.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					SegmentsEntryRoleImpl.class,
					segmentsEntryRole.getPrimaryKey()) == null) {

				cacheResult(segmentsEntryRole);
			}
			else {
				segmentsEntryRole.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all segments entry roles.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SegmentsEntryRoleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the segments entry role.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SegmentsEntryRole segmentsEntryRole) {
		entityCache.removeResult(
			SegmentsEntryRoleImpl.class, segmentsEntryRole.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SegmentsEntryRoleModelImpl)segmentsEntryRole, true);
	}

	@Override
	public void clearCache(List<SegmentsEntryRole> segmentsEntryRoles) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SegmentsEntryRole segmentsEntryRole : segmentsEntryRoles) {
			entityCache.removeResult(
				SegmentsEntryRoleImpl.class, segmentsEntryRole.getPrimaryKey());

			clearUniqueFindersCache(
				(SegmentsEntryRoleModelImpl)segmentsEntryRole, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SegmentsEntryRoleImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SegmentsEntryRoleModelImpl segmentsEntryRoleModelImpl) {

		Object[] args = new Object[] {
			segmentsEntryRoleModelImpl.getSegmentsEntryId(),
			segmentsEntryRoleModelImpl.getRoleId()
		};

		finderCache.putResult(
			_finderPathCountByS_R, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByS_R, args, segmentsEntryRoleModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SegmentsEntryRoleModelImpl segmentsEntryRoleModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsEntryRoleModelImpl.getSegmentsEntryId(),
				segmentsEntryRoleModelImpl.getRoleId()
			};

			finderCache.removeResult(_finderPathCountByS_R, args);
			finderCache.removeResult(_finderPathFetchByS_R, args);
		}

		if ((segmentsEntryRoleModelImpl.getColumnBitmask() &
			 _finderPathFetchByS_R.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsEntryRoleModelImpl.getOriginalSegmentsEntryId(),
				segmentsEntryRoleModelImpl.getOriginalRoleId()
			};

			finderCache.removeResult(_finderPathCountByS_R, args);
			finderCache.removeResult(_finderPathFetchByS_R, args);
		}
	}

	/**
	 * Creates a new segments entry role with the primary key. Does not add the segments entry role to the database.
	 *
	 * @param segmentsEntryRoleId the primary key for the new segments entry role
	 * @return the new segments entry role
	 */
	@Override
	public SegmentsEntryRole create(long segmentsEntryRoleId) {
		SegmentsEntryRole segmentsEntryRole = new SegmentsEntryRoleImpl();

		segmentsEntryRole.setNew(true);
		segmentsEntryRole.setPrimaryKey(segmentsEntryRoleId);

		segmentsEntryRole.setCompanyId(CompanyThreadLocal.getCompanyId());

		return segmentsEntryRole;
	}

	/**
	 * Removes the segments entry role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role that was removed
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole remove(long segmentsEntryRoleId)
		throws NoSuchEntryRoleException {

		return remove((Serializable)segmentsEntryRoleId);
	}

	/**
	 * Removes the segments entry role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the segments entry role
	 * @return the segments entry role that was removed
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole remove(Serializable primaryKey)
		throws NoSuchEntryRoleException {

		Session session = null;

		try {
			session = openSession();

			SegmentsEntryRole segmentsEntryRole =
				(SegmentsEntryRole)session.get(
					SegmentsEntryRoleImpl.class, primaryKey);

			if (segmentsEntryRole == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryRoleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(segmentsEntryRole);
		}
		catch (NoSuchEntryRoleException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SegmentsEntryRole removeImpl(
		SegmentsEntryRole segmentsEntryRole) {

		if (!ctPersistenceHelper.isRemove(segmentsEntryRole)) {
			return segmentsEntryRole;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsEntryRole)) {
				segmentsEntryRole = (SegmentsEntryRole)session.get(
					SegmentsEntryRoleImpl.class,
					segmentsEntryRole.getPrimaryKeyObj());
			}

			if (segmentsEntryRole != null) {
				session.delete(segmentsEntryRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (segmentsEntryRole != null) {
			clearCache(segmentsEntryRole);
		}

		return segmentsEntryRole;
	}

	@Override
	public SegmentsEntryRole updateImpl(SegmentsEntryRole segmentsEntryRole) {
		boolean isNew = segmentsEntryRole.isNew();

		if (!(segmentsEntryRole instanceof SegmentsEntryRoleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsEntryRole.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					segmentsEntryRole);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsEntryRole proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsEntryRole implementation " +
					segmentsEntryRole.getClass());
		}

		SegmentsEntryRoleModelImpl segmentsEntryRoleModelImpl =
			(SegmentsEntryRoleModelImpl)segmentsEntryRole;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (segmentsEntryRole.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsEntryRole.setCreateDate(now);
			}
			else {
				segmentsEntryRole.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!segmentsEntryRoleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsEntryRole.setModifiedDate(now);
			}
			else {
				segmentsEntryRole.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(segmentsEntryRole)) {
				if (!isNew) {
					session.evict(
						SegmentsEntryRoleImpl.class,
						segmentsEntryRole.getPrimaryKeyObj());
				}

				session.save(segmentsEntryRole);

				segmentsEntryRole.setNew(false);
			}
			else {
				segmentsEntryRole = (SegmentsEntryRole)session.merge(
					segmentsEntryRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (segmentsEntryRole.getCtCollectionId() != 0) {
			segmentsEntryRole.resetOriginalValues();

			return segmentsEntryRole;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				segmentsEntryRoleModelImpl.getSegmentsEntryId()
			};

			finderCache.removeResult(_finderPathCountBySegmentsEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySegmentsEntryId, args);

			args = new Object[] {segmentsEntryRoleModelImpl.getRoleId()};

			finderCache.removeResult(_finderPathCountByRoleId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByRoleId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((segmentsEntryRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySegmentsEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsEntryRoleModelImpl.getOriginalSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);

				args = new Object[] {
					segmentsEntryRoleModelImpl.getSegmentsEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsEntryId, args);
			}

			if ((segmentsEntryRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRoleId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryRoleModelImpl.getOriginalRoleId()
				};

				finderCache.removeResult(_finderPathCountByRoleId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);

				args = new Object[] {segmentsEntryRoleModelImpl.getRoleId()};

				finderCache.removeResult(_finderPathCountByRoleId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);
			}
		}

		entityCache.putResult(
			SegmentsEntryRoleImpl.class, segmentsEntryRole.getPrimaryKey(),
			segmentsEntryRole, false);

		clearUniqueFindersCache(segmentsEntryRoleModelImpl, false);
		cacheUniqueFindersCache(segmentsEntryRoleModelImpl);

		segmentsEntryRole.resetOriginalValues();

		return segmentsEntryRole;
	}

	/**
	 * Returns the segments entry role with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments entry role
	 * @return the segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryRoleException {

		SegmentsEntryRole segmentsEntryRole = fetchByPrimaryKey(primaryKey);

		if (segmentsEntryRole == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryRoleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return segmentsEntryRole;
	}

	/**
	 * Returns the segments entry role with the primary key or throws a <code>NoSuchEntryRoleException</code> if it could not be found.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole findByPrimaryKey(long segmentsEntryRoleId)
		throws NoSuchEntryRoleException {

		return findByPrimaryKey((Serializable)segmentsEntryRoleId);
	}

	/**
	 * Returns the segments entry role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments entry role
	 * @return the segments entry role, or <code>null</code> if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(SegmentsEntryRole.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		SegmentsEntryRole segmentsEntryRole = null;

		Session session = null;

		try {
			session = openSession();

			segmentsEntryRole = (SegmentsEntryRole)session.get(
				SegmentsEntryRoleImpl.class, primaryKey);

			if (segmentsEntryRole != null) {
				cacheResult(segmentsEntryRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return segmentsEntryRole;
	}

	/**
	 * Returns the segments entry role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role, or <code>null</code> if a segments entry role with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRole fetchByPrimaryKey(long segmentsEntryRoleId) {
		return fetchByPrimaryKey((Serializable)segmentsEntryRoleId);
	}

	@Override
	public Map<Serializable, SegmentsEntryRole> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(SegmentsEntryRole.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SegmentsEntryRole> map =
			new HashMap<Serializable, SegmentsEntryRole>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SegmentsEntryRole segmentsEntryRole = fetchByPrimaryKey(primaryKey);

			if (segmentsEntryRole != null) {
				map.put(primaryKey, segmentsEntryRole);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (SegmentsEntryRole segmentsEntryRole :
					(List<SegmentsEntryRole>)query.list()) {

				map.put(
					segmentsEntryRole.getPrimaryKeyObj(), segmentsEntryRole);

				cacheResult(segmentsEntryRole);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the segments entry roles.
	 *
	 * @return the segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findAll(
		int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments entry roles
	 */
	@Override
	public List<SegmentsEntryRole> findAll(
		int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<SegmentsEntryRole> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntryRole>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SEGMENTSENTRYROLE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SEGMENTSENTRYROLE;

				sql = sql.concat(SegmentsEntryRoleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SegmentsEntryRole>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the segments entry roles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SegmentsEntryRole segmentsEntryRole : findAll()) {
			remove(segmentsEntryRole);
		}
	}

	/**
	 * Returns the number of segments entry roles.
	 *
	 * @return the number of segments entry roles
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntryRole.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SEGMENTSENTRYROLE);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "segmentsEntryRoleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEGMENTSENTRYROLE;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return SegmentsEntryRoleModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SegmentsEntryRole";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("segmentsEntryId");
		ctStrictColumnNames.add("roleId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("segmentsEntryRoleId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"segmentsEntryId", "roleId"});
	}

	/**
	 * Initializes the segments entry role persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SegmentsEntryRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SegmentsEntryRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindBySegmentsEntryId = new FinderPath(
			SegmentsEntryRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySegmentsEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySegmentsEntryId = new FinderPath(
			SegmentsEntryRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySegmentsEntryId",
			new String[] {Long.class.getName()},
			SegmentsEntryRoleModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK);

		_finderPathCountBySegmentsEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySegmentsEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByRoleId = new FinderPath(
			SegmentsEntryRoleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRoleId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRoleId = new FinderPath(
			SegmentsEntryRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
			new String[] {Long.class.getName()},
			SegmentsEntryRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByRoleId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRoleId", new String[] {Long.class.getName()});

		_finderPathFetchByS_R = new FinderPath(
			SegmentsEntryRoleImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByS_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			SegmentsEntryRoleModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK |
			SegmentsEntryRoleModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByS_R = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_R",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SegmentsEntryRoleImpl.class.getName());
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

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SEGMENTSENTRYROLE =
		"SELECT segmentsEntryRole FROM SegmentsEntryRole segmentsEntryRole";

	private static final String _SQL_SELECT_SEGMENTSENTRYROLE_WHERE =
		"SELECT segmentsEntryRole FROM SegmentsEntryRole segmentsEntryRole WHERE ";

	private static final String _SQL_COUNT_SEGMENTSENTRYROLE =
		"SELECT COUNT(segmentsEntryRole) FROM SegmentsEntryRole segmentsEntryRole";

	private static final String _SQL_COUNT_SEGMENTSENTRYROLE_WHERE =
		"SELECT COUNT(segmentsEntryRole) FROM SegmentsEntryRole segmentsEntryRole WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "segmentsEntryRole.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SegmentsEntryRole exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SegmentsEntryRole exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryRolePersistenceImpl.class);

	static {
		try {
			Class.forName(SegmentsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}