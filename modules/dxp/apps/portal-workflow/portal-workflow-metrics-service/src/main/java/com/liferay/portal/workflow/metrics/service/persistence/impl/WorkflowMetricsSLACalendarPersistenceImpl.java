/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLACalendarException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarImpl;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarModelImpl;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLACalendarPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the workflow metrics sla calendar service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLACalendarPersistenceImpl
	extends BasePersistenceImpl<WorkflowMetricsSLACalendar>
	implements WorkflowMetricsSLACalendarPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WorkflowMetricsSLACalendarUtil</code> to access the workflow metrics sla calendar persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WorkflowMetricsSLACalendarImpl.class.getName();

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
	 * Returns all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @return the range of matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] {uuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<WorkflowMetricsSLACalendar> list = null;

		if (retrieveFromCache) {
			list = (List<WorkflowMetricsSLACalendar>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
						list) {

					if (!uuid.equals(workflowMetricsSLACalendar.getUuid())) {
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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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
			else if (pagination) {
				query.append(WorkflowMetricsSLACalendarModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<WorkflowMetricsSLACalendar>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLACalendar>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			fetchByUuid_First(uuid, orderByComparator);

		if (workflowMetricsSLACalendar != null) {
			return workflowMetricsSLACalendar;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLACalendarException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		List<WorkflowMetricsSLACalendar> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			fetchByUuid_Last(uuid, orderByComparator);

		if (workflowMetricsSLACalendar != null) {
			return workflowMetricsSLACalendar;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLACalendarException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLACalendar> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla calendars before and after the current workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the current workflow metrics sla calendar
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar[] findByUuid_PrevAndNext(
			long workflowMetricsSLACalendarId, String uuid,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			findByPrimaryKey(workflowMetricsSLACalendarId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLACalendar[] array =
				new WorkflowMetricsSLACalendarImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, workflowMetricsSLACalendar, uuid, orderByComparator,
				true);

			array[1] = workflowMetricsSLACalendar;

			array[2] = getByUuid_PrevAndNext(
				session, workflowMetricsSLACalendar, uuid, orderByComparator,
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

	protected WorkflowMetricsSLACalendar getByUuid_PrevAndNext(
		Session session, WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
		String uuid,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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
			query.append(WorkflowMetricsSLACalendarModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLACalendar)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLACalendar> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla calendars where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLACalendar);
		}
	}

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla calendars
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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
		"workflowMetricsSLACalendar.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(workflowMetricsSLACalendar.uuid IS NULL OR workflowMetricsSLACalendar.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLACalendarException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar findByUUID_G(String uuid, long groupId)
		throws NoSuchSLACalendarException {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar = fetchByUUID_G(
			uuid, groupId);

		if (workflowMetricsSLACalendar == null) {
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

			throw new NoSuchSLACalendarException(msg.toString());
		}

		return workflowMetricsSLACalendar;
	}

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof WorkflowMetricsSLACalendar) {
			WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
				(WorkflowMetricsSLACalendar)result;

			if (!Objects.equals(uuid, workflowMetricsSLACalendar.getUuid()) ||
				(groupId != workflowMetricsSLACalendar.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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

				List<WorkflowMetricsSLACalendar> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
						list.get(0);

					result = workflowMetricsSLACalendar;

					cacheResult(workflowMetricsSLACalendar);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByUUID_G, finderArgs);

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
			return (WorkflowMetricsSLACalendar)result;
		}
	}

	/**
	 * Removes the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla calendar that was removed
	 */
	@Override
	public WorkflowMetricsSLACalendar removeByUUID_G(String uuid, long groupId)
		throws NoSuchSLACalendarException {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar = findByUUID_G(
			uuid, groupId);

		return remove(workflowMetricsSLACalendar);
	}

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla calendars
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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
		"workflowMetricsSLACalendar.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(workflowMetricsSLACalendar.uuid IS NULL OR workflowMetricsSLACalendar.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"workflowMetricsSLACalendar.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @return the range of matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] {uuid, companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<WorkflowMetricsSLACalendar> list = null;

		if (retrieveFromCache) {
			list = (List<WorkflowMetricsSLACalendar>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
						list) {

					if (!uuid.equals(workflowMetricsSLACalendar.getUuid()) ||
						(companyId !=
							workflowMetricsSLACalendar.getCompanyId())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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
			else if (pagination) {
				query.append(WorkflowMetricsSLACalendarModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<WorkflowMetricsSLACalendar>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLACalendar>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (workflowMetricsSLACalendar != null) {
			return workflowMetricsSLACalendar;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLACalendarException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		List<WorkflowMetricsSLACalendar> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (workflowMetricsSLACalendar != null) {
			return workflowMetricsSLACalendar;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLACalendarException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLACalendar> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla calendars before and after the current workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the current workflow metrics sla calendar
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLACalendarId, String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			findByPrimaryKey(workflowMetricsSLACalendarId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLACalendar[] array =
				new WorkflowMetricsSLACalendarImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLACalendar, uuid, companyId,
				orderByComparator, true);

			array[1] = workflowMetricsSLACalendar;

			array[2] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLACalendar, uuid, companyId,
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

	protected WorkflowMetricsSLACalendar getByUuid_C_PrevAndNext(
		Session session, WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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
			query.append(WorkflowMetricsSLACalendarModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLACalendar)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLACalendar> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(workflowMetricsSLACalendar);
		}
	}

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla calendars
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLACALENDAR_WHERE);

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
		"workflowMetricsSLACalendar.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(workflowMetricsSLACalendar.uuid IS NULL OR workflowMetricsSLACalendar.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"workflowMetricsSLACalendar.companyId = ?";

	public WorkflowMetricsSLACalendarPersistenceImpl() {
		setModelClass(WorkflowMetricsSLACalendar.class);

		setModelImplClass(WorkflowMetricsSLACalendarImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the workflow metrics sla calendar in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACalendar the workflow metrics sla calendar
	 */
	@Override
	public void cacheResult(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		entityCache.putResult(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			workflowMetricsSLACalendar.getPrimaryKey(),
			workflowMetricsSLACalendar);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				workflowMetricsSLACalendar.getUuid(),
				workflowMetricsSLACalendar.getGroupId()
			},
			workflowMetricsSLACalendar);

		workflowMetricsSLACalendar.resetOriginalValues();
	}

	/**
	 * Caches the workflow metrics sla calendars in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACalendars the workflow metrics sla calendars
	 */
	@Override
	public void cacheResult(
		List<WorkflowMetricsSLACalendar> workflowMetricsSLACalendars) {

		for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
				workflowMetricsSLACalendars) {

			if (entityCache.getResult(
					WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
					WorkflowMetricsSLACalendarImpl.class,
					workflowMetricsSLACalendar.getPrimaryKey()) == null) {

				cacheResult(workflowMetricsSLACalendar);
			}
			else {
				workflowMetricsSLACalendar.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all workflow metrics sla calendars.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WorkflowMetricsSLACalendarImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the workflow metrics sla calendar.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		entityCache.removeResult(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			workflowMetricsSLACalendar.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(WorkflowMetricsSLACalendarModelImpl)workflowMetricsSLACalendar,
			true);
	}

	@Override
	public void clearCache(
		List<WorkflowMetricsSLACalendar> workflowMetricsSLACalendars) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
				workflowMetricsSLACalendars) {

			entityCache.removeResult(
				WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
				WorkflowMetricsSLACalendarImpl.class,
				workflowMetricsSLACalendar.getPrimaryKey());

			clearUniqueFindersCache(
				(WorkflowMetricsSLACalendarModelImpl)workflowMetricsSLACalendar,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		WorkflowMetricsSLACalendarModelImpl
			workflowMetricsSLACalendarModelImpl) {

		Object[] args = new Object[] {
			workflowMetricsSLACalendarModelImpl.getUuid(),
			workflowMetricsSLACalendarModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, workflowMetricsSLACalendarModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		WorkflowMetricsSLACalendarModelImpl workflowMetricsSLACalendarModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				workflowMetricsSLACalendarModelImpl.getUuid(),
				workflowMetricsSLACalendarModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((workflowMetricsSLACalendarModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				workflowMetricsSLACalendarModelImpl.getOriginalUuid(),
				workflowMetricsSLACalendarModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new workflow metrics sla calendar with the primary key. Does not add the workflow metrics sla calendar to the database.
	 *
	 * @param workflowMetricsSLACalendarId the primary key for the new workflow metrics sla calendar
	 * @return the new workflow metrics sla calendar
	 */
	@Override
	public WorkflowMetricsSLACalendar create(
		long workflowMetricsSLACalendarId) {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			new WorkflowMetricsSLACalendarImpl();

		workflowMetricsSLACalendar.setNew(true);
		workflowMetricsSLACalendar.setPrimaryKey(workflowMetricsSLACalendarId);

		String uuid = PortalUUIDUtil.generate();

		workflowMetricsSLACalendar.setUuid(uuid);

		workflowMetricsSLACalendar.setCompanyId(companyProvider.getCompanyId());

		return workflowMetricsSLACalendar;
	}

	/**
	 * Removes the workflow metrics sla calendar with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was removed
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar remove(long workflowMetricsSLACalendarId)
		throws NoSuchSLACalendarException {

		return remove((Serializable)workflowMetricsSLACalendarId);
	}

	/**
	 * Removes the workflow metrics sla calendar with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was removed
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar remove(Serializable primaryKey)
		throws NoSuchSLACalendarException {

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
				(WorkflowMetricsSLACalendar)session.get(
					WorkflowMetricsSLACalendarImpl.class, primaryKey);

			if (workflowMetricsSLACalendar == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSLACalendarException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(workflowMetricsSLACalendar);
		}
		catch (NoSuchSLACalendarException nsee) {
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
	protected WorkflowMetricsSLACalendar removeImpl(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(workflowMetricsSLACalendar)) {
				workflowMetricsSLACalendar =
					(WorkflowMetricsSLACalendar)session.get(
						WorkflowMetricsSLACalendarImpl.class,
						workflowMetricsSLACalendar.getPrimaryKeyObj());
			}

			if (workflowMetricsSLACalendar != null) {
				session.delete(workflowMetricsSLACalendar);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (workflowMetricsSLACalendar != null) {
			clearCache(workflowMetricsSLACalendar);
		}

		return workflowMetricsSLACalendar;
	}

	@Override
	public WorkflowMetricsSLACalendar updateImpl(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		boolean isNew = workflowMetricsSLACalendar.isNew();

		if (!(workflowMetricsSLACalendar instanceof
				WorkflowMetricsSLACalendarModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(workflowMetricsSLACalendar.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					workflowMetricsSLACalendar);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in workflowMetricsSLACalendar proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WorkflowMetricsSLACalendar implementation " +
					workflowMetricsSLACalendar.getClass());
		}

		WorkflowMetricsSLACalendarModelImpl
			workflowMetricsSLACalendarModelImpl =
				(WorkflowMetricsSLACalendarModelImpl)workflowMetricsSLACalendar;

		if (Validator.isNull(workflowMetricsSLACalendar.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			workflowMetricsSLACalendar.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (workflowMetricsSLACalendar.getCreateDate() == null)) {
			if (serviceContext == null) {
				workflowMetricsSLACalendar.setCreateDate(now);
			}
			else {
				workflowMetricsSLACalendar.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!workflowMetricsSLACalendarModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				workflowMetricsSLACalendar.setModifiedDate(now);
			}
			else {
				workflowMetricsSLACalendar.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (workflowMetricsSLACalendar.isNew()) {
				session.save(workflowMetricsSLACalendar);

				workflowMetricsSLACalendar.setNew(false);
			}
			else {
				workflowMetricsSLACalendar =
					(WorkflowMetricsSLACalendar)session.merge(
						workflowMetricsSLACalendar);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!WorkflowMetricsSLACalendarModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				workflowMetricsSLACalendarModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				workflowMetricsSLACalendarModelImpl.getUuid(),
				workflowMetricsSLACalendarModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((workflowMetricsSLACalendarModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLACalendarModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					workflowMetricsSLACalendarModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((workflowMetricsSLACalendarModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLACalendarModelImpl.getOriginalUuid(),
					workflowMetricsSLACalendarModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					workflowMetricsSLACalendarModelImpl.getUuid(),
					workflowMetricsSLACalendarModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			workflowMetricsSLACalendar.getPrimaryKey(),
			workflowMetricsSLACalendar, false);

		clearUniqueFindersCache(workflowMetricsSLACalendarModelImpl, false);
		cacheUniqueFindersCache(workflowMetricsSLACalendarModelImpl);

		workflowMetricsSLACalendar.resetOriginalValues();

		return workflowMetricsSLACalendar;
	}

	/**
	 * Returns the workflow metrics sla calendar with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSLACalendarException {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			fetchByPrimaryKey(primaryKey);

		if (workflowMetricsSLACalendar == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSLACalendarException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return workflowMetricsSLACalendar;
	}

	/**
	 * Returns the workflow metrics sla calendar with the primary key or throws a <code>NoSuchSLACalendarException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar findByPrimaryKey(
			long workflowMetricsSLACalendarId)
		throws NoSuchSLACalendarException {

		return findByPrimaryKey((Serializable)workflowMetricsSLACalendarId);
	}

	/**
	 * Returns the workflow metrics sla calendar with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar, or <code>null</code> if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACalendar fetchByPrimaryKey(
		long workflowMetricsSLACalendarId) {

		return fetchByPrimaryKey((Serializable)workflowMetricsSLACalendarId);
	}

	/**
	 * Returns all the workflow metrics sla calendars.
	 *
	 * @return the workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla calendars.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @return the range of workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of workflow metrics sla calendars
	 */
	@Override
	public List<WorkflowMetricsSLACalendar> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<WorkflowMetricsSLACalendar> list = null;

		if (retrieveFromCache) {
			list = (List<WorkflowMetricsSLACalendar>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_WORKFLOWMETRICSSLACALENDAR);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WORKFLOWMETRICSSLACALENDAR;

				if (pagination) {
					sql = sql.concat(
						WorkflowMetricsSLACalendarModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WorkflowMetricsSLACalendar>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLACalendar>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the workflow metrics sla calendars from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
				findAll()) {

			remove(workflowMetricsSLACalendar);
		}
	}

	/**
	 * Returns the number of workflow metrics sla calendars.
	 *
	 * @return the number of workflow metrics sla calendars
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_WORKFLOWMETRICSSLACALENDAR);

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
		return "workflowMetricsSLACalendarId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WORKFLOWMETRICSSLACALENDAR;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WorkflowMetricsSLACalendarModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the workflow metrics sla calendar persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			WorkflowMetricsSLACalendarModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLACalendarModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLACalendarModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLACalendarImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLACalendarModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLACalendarModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			WorkflowMetricsSLACalendarModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLACalendarModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(WorkflowMetricsSLACalendarImpl.class.getName());
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

	private static final String _SQL_SELECT_WORKFLOWMETRICSSLACALENDAR =
		"SELECT workflowMetricsSLACalendar FROM WorkflowMetricsSLACalendar workflowMetricsSLACalendar";

	private static final String _SQL_SELECT_WORKFLOWMETRICSSLACALENDAR_WHERE =
		"SELECT workflowMetricsSLACalendar FROM WorkflowMetricsSLACalendar workflowMetricsSLACalendar WHERE ";

	private static final String _SQL_COUNT_WORKFLOWMETRICSSLACALENDAR =
		"SELECT COUNT(workflowMetricsSLACalendar) FROM WorkflowMetricsSLACalendar workflowMetricsSLACalendar";

	private static final String _SQL_COUNT_WORKFLOWMETRICSSLACALENDAR_WHERE =
		"SELECT COUNT(workflowMetricsSLACalendar) FROM WorkflowMetricsSLACalendar workflowMetricsSLACalendar WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"workflowMetricsSLACalendar.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WorkflowMetricsSLACalendar exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WorkflowMetricsSLACalendar exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsSLACalendarPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}