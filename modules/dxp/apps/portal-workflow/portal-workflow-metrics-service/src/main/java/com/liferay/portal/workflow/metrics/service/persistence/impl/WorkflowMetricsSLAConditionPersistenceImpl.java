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
import com.liferay.portal.workflow.metrics.exception.NoSuchSLAConditionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionImpl;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionModelImpl;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLAConditionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the workflow metrics sla condition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLAConditionPersistenceImpl
	extends BasePersistenceImpl<WorkflowMetricsSLACondition>
	implements WorkflowMetricsSLAConditionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WorkflowMetricsSLAConditionUtil</code> to access the workflow metrics sla condition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WorkflowMetricsSLAConditionImpl.class.getName();

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
	 * Returns all the workflow metrics sla conditions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla conditions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @return the range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
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

		List<WorkflowMetricsSLACondition> list = null;

		if (retrieveFromCache) {
			list = (List<WorkflowMetricsSLACondition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
						list) {

					if (!uuid.equals(workflowMetricsSLACondition.getUuid())) {
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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE);

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
				query.append(
					WorkflowMetricsSLAConditionModelImpl.ORDER_BY_JPQL);
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
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
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
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			fetchByUuid_First(uuid, orderByComparator);

		if (workflowMetricsSLACondition != null) {
			return workflowMetricsSLACondition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLAConditionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		List<WorkflowMetricsSLACondition> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			fetchByUuid_Last(uuid, orderByComparator);

		if (workflowMetricsSLACondition != null) {
			return workflowMetricsSLACondition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLAConditionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLACondition> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla conditions before and after the current workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the current workflow metrics sla condition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition[] findByUuid_PrevAndNext(
			long workflowMetricsSLAConditionId, String uuid,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			findByPrimaryKey(workflowMetricsSLAConditionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLACondition[] array =
				new WorkflowMetricsSLAConditionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, workflowMetricsSLACondition, uuid, orderByComparator,
				true);

			array[1] = workflowMetricsSLACondition;

			array[2] = getByUuid_PrevAndNext(
				session, workflowMetricsSLACondition, uuid, orderByComparator,
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

	protected WorkflowMetricsSLACondition getByUuid_PrevAndNext(
		Session session,
		WorkflowMetricsSLACondition workflowMetricsSLACondition, String uuid,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE);

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
			query.append(WorkflowMetricsSLAConditionModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLACondition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLACondition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla conditions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLACondition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla conditions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLACONDITION_WHERE);

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
		"workflowMetricsSLACondition.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(workflowMetricsSLACondition.uuid IS NULL OR workflowMetricsSLACondition.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLAConditionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByUUID_G(String uuid, long groupId)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition = fetchByUUID_G(
			uuid, groupId);

		if (workflowMetricsSLACondition == null) {
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

			throw new NoSuchSLAConditionException(msg.toString());
		}

		return workflowMetricsSLACondition;
	}

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof WorkflowMetricsSLACondition) {
			WorkflowMetricsSLACondition workflowMetricsSLACondition =
				(WorkflowMetricsSLACondition)result;

			if (!Objects.equals(uuid, workflowMetricsSLACondition.getUuid()) ||
				(groupId != workflowMetricsSLACondition.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE);

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

				List<WorkflowMetricsSLACondition> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					WorkflowMetricsSLACondition workflowMetricsSLACondition =
						list.get(0);

					result = workflowMetricsSLACondition;

					cacheResult(workflowMetricsSLACondition);
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
			return (WorkflowMetricsSLACondition)result;
		}
	}

	/**
	 * Removes the workflow metrics sla condition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla condition that was removed
	 */
	@Override
	public WorkflowMetricsSLACondition removeByUUID_G(String uuid, long groupId)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition = findByUUID_G(
			uuid, groupId);

		return remove(workflowMetricsSLACondition);
	}

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLACONDITION_WHERE);

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
		"workflowMetricsSLACondition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(workflowMetricsSLACondition.uuid IS NULL OR workflowMetricsSLACondition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"workflowMetricsSLACondition.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @return the range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
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

		List<WorkflowMetricsSLACondition> list = null;

		if (retrieveFromCache) {
			list = (List<WorkflowMetricsSLACondition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
						list) {

					if (!uuid.equals(workflowMetricsSLACondition.getUuid()) ||
						(companyId !=
							workflowMetricsSLACondition.getCompanyId())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE);

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
				query.append(
					WorkflowMetricsSLAConditionModelImpl.ORDER_BY_JPQL);
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
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
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
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (workflowMetricsSLACondition != null) {
			return workflowMetricsSLACondition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLAConditionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		List<WorkflowMetricsSLACondition> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (workflowMetricsSLACondition != null) {
			return workflowMetricsSLACondition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLAConditionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLACondition> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla conditions before and after the current workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the current workflow metrics sla condition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLAConditionId, String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			findByPrimaryKey(workflowMetricsSLAConditionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLACondition[] array =
				new WorkflowMetricsSLAConditionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLACondition, uuid, companyId,
				orderByComparator, true);

			array[1] = workflowMetricsSLACondition;

			array[2] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLACondition, uuid, companyId,
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

	protected WorkflowMetricsSLACondition getByUuid_C_PrevAndNext(
		Session session,
		WorkflowMetricsSLACondition workflowMetricsSLACondition, String uuid,
		long companyId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE);

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
			query.append(WorkflowMetricsSLAConditionModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLACondition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLACondition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(workflowMetricsSLACondition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLACONDITION_WHERE);

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
		"workflowMetricsSLACondition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(workflowMetricsSLACondition.uuid IS NULL OR workflowMetricsSLACondition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"workflowMetricsSLACondition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_WMSLADI;
	private FinderPath _finderPathWithoutPaginationFindByC_WMSLADI;
	private FinderPath _finderPathCountByC_WMSLADI;

	/**
	 * Returns all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId) {

		return findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @return the range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start,
		int end) {

		return findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_WMSLADI;
			finderArgs = new Object[] {
				companyId, workflowMetricsSLADefinitionId
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_WMSLADI;
			finderArgs = new Object[] {
				companyId, workflowMetricsSLADefinitionId, start, end,
				orderByComparator
			};
		}

		List<WorkflowMetricsSLACondition> list = null;

		if (retrieveFromCache) {
			list = (List<WorkflowMetricsSLACondition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
						list) {

					if ((companyId !=
							workflowMetricsSLACondition.getCompanyId()) ||
						(workflowMetricsSLADefinitionId !=
							workflowMetricsSLACondition.
								getWorkflowMetricsSLADefinitionId())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE);

			query.append(_FINDER_COLUMN_C_WMSLADI_COMPANYID_2);

			query.append(
				_FINDER_COLUMN_C_WMSLADI_WORKFLOWMETRICSSLADEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					WorkflowMetricsSLAConditionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(workflowMetricsSLADefinitionId);

				if (!pagination) {
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
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
	 * Returns the first workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByC_WMSLADI_First(
			long companyId, long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			fetchByC_WMSLADI_First(
				companyId, workflowMetricsSLADefinitionId, orderByComparator);

		if (workflowMetricsSLACondition != null) {
			return workflowMetricsSLACondition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", workflowMetricsSLADefinitionId=");
		msg.append(workflowMetricsSLADefinitionId);

		msg.append("}");

		throw new NoSuchSLAConditionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByC_WMSLADI_First(
		long companyId, long workflowMetricsSLADefinitionId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		List<WorkflowMetricsSLACondition> list = findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByC_WMSLADI_Last(
			long companyId, long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			fetchByC_WMSLADI_Last(
				companyId, workflowMetricsSLADefinitionId, orderByComparator);

		if (workflowMetricsSLACondition != null) {
			return workflowMetricsSLACondition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", workflowMetricsSLADefinitionId=");
		msg.append(workflowMetricsSLADefinitionId);

		msg.append("}");

		throw new NoSuchSLAConditionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByC_WMSLADI_Last(
		long companyId, long workflowMetricsSLADefinitionId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		int count = countByC_WMSLADI(companyId, workflowMetricsSLADefinitionId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLACondition> list = findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla conditions before and after the current workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the current workflow metrics sla condition
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition[] findByC_WMSLADI_PrevAndNext(
			long workflowMetricsSLAConditionId, long companyId,
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			findByPrimaryKey(workflowMetricsSLAConditionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLACondition[] array =
				new WorkflowMetricsSLAConditionImpl[3];

			array[0] = getByC_WMSLADI_PrevAndNext(
				session, workflowMetricsSLACondition, companyId,
				workflowMetricsSLADefinitionId, orderByComparator, true);

			array[1] = workflowMetricsSLACondition;

			array[2] = getByC_WMSLADI_PrevAndNext(
				session, workflowMetricsSLACondition, companyId,
				workflowMetricsSLADefinitionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLACondition getByC_WMSLADI_PrevAndNext(
		Session session,
		WorkflowMetricsSLACondition workflowMetricsSLACondition, long companyId,
		long workflowMetricsSLADefinitionId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE);

		query.append(_FINDER_COLUMN_C_WMSLADI_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_WMSLADI_WORKFLOWMETRICSSLADEFINITIONID_2);

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
			query.append(WorkflowMetricsSLAConditionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(workflowMetricsSLADefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLACondition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLACondition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 */
	@Override
	public void removeByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId) {

		for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
				findByC_WMSLADI(
					companyId, workflowMetricsSLADefinitionId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLACondition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	@Override
	public int countByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId) {

		FinderPath finderPath = _finderPathCountByC_WMSLADI;

		Object[] finderArgs = new Object[] {
			companyId, workflowMetricsSLADefinitionId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLACONDITION_WHERE);

			query.append(_FINDER_COLUMN_C_WMSLADI_COMPANYID_2);

			query.append(
				_FINDER_COLUMN_C_WMSLADI_WORKFLOWMETRICSSLADEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(workflowMetricsSLADefinitionId);

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

	private static final String _FINDER_COLUMN_C_WMSLADI_COMPANYID_2 =
		"workflowMetricsSLACondition.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_WMSLADI_WORKFLOWMETRICSSLADEFINITIONID_2 =
			"workflowMetricsSLACondition.workflowMetricsSLADefinitionId = ?";

	public WorkflowMetricsSLAConditionPersistenceImpl() {
		setModelClass(WorkflowMetricsSLACondition.class);

		setModelImplClass(WorkflowMetricsSLAConditionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the workflow metrics sla condition in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 */
	@Override
	public void cacheResult(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		entityCache.putResult(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			workflowMetricsSLACondition.getPrimaryKey(),
			workflowMetricsSLACondition);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				workflowMetricsSLACondition.getUuid(),
				workflowMetricsSLACondition.getGroupId()
			},
			workflowMetricsSLACondition);

		workflowMetricsSLACondition.resetOriginalValues();
	}

	/**
	 * Caches the workflow metrics sla conditions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLAConditions the workflow metrics sla conditions
	 */
	@Override
	public void cacheResult(
		List<WorkflowMetricsSLACondition> workflowMetricsSLAConditions) {

		for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
				workflowMetricsSLAConditions) {

			if (entityCache.getResult(
					WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
					WorkflowMetricsSLAConditionImpl.class,
					workflowMetricsSLACondition.getPrimaryKey()) == null) {

				cacheResult(workflowMetricsSLACondition);
			}
			else {
				workflowMetricsSLACondition.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all workflow metrics sla conditions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WorkflowMetricsSLAConditionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the workflow metrics sla condition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		entityCache.removeResult(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			workflowMetricsSLACondition.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(WorkflowMetricsSLAConditionModelImpl)workflowMetricsSLACondition,
			true);
	}

	@Override
	public void clearCache(
		List<WorkflowMetricsSLACondition> workflowMetricsSLAConditions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
				workflowMetricsSLAConditions) {

			entityCache.removeResult(
				WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
				WorkflowMetricsSLAConditionImpl.class,
				workflowMetricsSLACondition.getPrimaryKey());

			clearUniqueFindersCache(
				(WorkflowMetricsSLAConditionModelImpl)
					workflowMetricsSLACondition,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		WorkflowMetricsSLAConditionModelImpl
			workflowMetricsSLAConditionModelImpl) {

		Object[] args = new Object[] {
			workflowMetricsSLAConditionModelImpl.getUuid(),
			workflowMetricsSLAConditionModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			workflowMetricsSLAConditionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		WorkflowMetricsSLAConditionModelImpl
			workflowMetricsSLAConditionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				workflowMetricsSLAConditionModelImpl.getUuid(),
				workflowMetricsSLAConditionModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((workflowMetricsSLAConditionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				workflowMetricsSLAConditionModelImpl.getOriginalUuid(),
				workflowMetricsSLAConditionModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new workflow metrics sla condition with the primary key. Does not add the workflow metrics sla condition to the database.
	 *
	 * @param workflowMetricsSLAConditionId the primary key for the new workflow metrics sla condition
	 * @return the new workflow metrics sla condition
	 */
	@Override
	public WorkflowMetricsSLACondition create(
		long workflowMetricsSLAConditionId) {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			new WorkflowMetricsSLAConditionImpl();

		workflowMetricsSLACondition.setNew(true);
		workflowMetricsSLACondition.setPrimaryKey(
			workflowMetricsSLAConditionId);

		String uuid = PortalUUIDUtil.generate();

		workflowMetricsSLACondition.setUuid(uuid);

		workflowMetricsSLACondition.setCompanyId(
			companyProvider.getCompanyId());

		return workflowMetricsSLACondition;
	}

	/**
	 * Removes the workflow metrics sla condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition remove(
			long workflowMetricsSLAConditionId)
		throws NoSuchSLAConditionException {

		return remove((Serializable)workflowMetricsSLAConditionId);
	}

	/**
	 * Removes the workflow metrics sla condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition remove(Serializable primaryKey)
		throws NoSuchSLAConditionException {

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLACondition workflowMetricsSLACondition =
				(WorkflowMetricsSLACondition)session.get(
					WorkflowMetricsSLAConditionImpl.class, primaryKey);

			if (workflowMetricsSLACondition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSLAConditionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(workflowMetricsSLACondition);
		}
		catch (NoSuchSLAConditionException nsee) {
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
	protected WorkflowMetricsSLACondition removeImpl(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(workflowMetricsSLACondition)) {
				workflowMetricsSLACondition =
					(WorkflowMetricsSLACondition)session.get(
						WorkflowMetricsSLAConditionImpl.class,
						workflowMetricsSLACondition.getPrimaryKeyObj());
			}

			if (workflowMetricsSLACondition != null) {
				session.delete(workflowMetricsSLACondition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (workflowMetricsSLACondition != null) {
			clearCache(workflowMetricsSLACondition);
		}

		return workflowMetricsSLACondition;
	}

	@Override
	public WorkflowMetricsSLACondition updateImpl(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		boolean isNew = workflowMetricsSLACondition.isNew();

		if (!(workflowMetricsSLACondition instanceof
				WorkflowMetricsSLAConditionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					workflowMetricsSLACondition.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					workflowMetricsSLACondition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in workflowMetricsSLACondition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WorkflowMetricsSLACondition implementation " +
					workflowMetricsSLACondition.getClass());
		}

		WorkflowMetricsSLAConditionModelImpl
			workflowMetricsSLAConditionModelImpl =
				(WorkflowMetricsSLAConditionModelImpl)
					workflowMetricsSLACondition;

		if (Validator.isNull(workflowMetricsSLACondition.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			workflowMetricsSLACondition.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (workflowMetricsSLACondition.getCreateDate() == null)) {
			if (serviceContext == null) {
				workflowMetricsSLACondition.setCreateDate(now);
			}
			else {
				workflowMetricsSLACondition.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!workflowMetricsSLAConditionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				workflowMetricsSLACondition.setModifiedDate(now);
			}
			else {
				workflowMetricsSLACondition.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (workflowMetricsSLACondition.isNew()) {
				session.save(workflowMetricsSLACondition);

				workflowMetricsSLACondition.setNew(false);
			}
			else {
				workflowMetricsSLACondition =
					(WorkflowMetricsSLACondition)session.merge(
						workflowMetricsSLACondition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!WorkflowMetricsSLAConditionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				workflowMetricsSLAConditionModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				workflowMetricsSLAConditionModelImpl.getUuid(),
				workflowMetricsSLAConditionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				workflowMetricsSLAConditionModelImpl.getCompanyId(),
				workflowMetricsSLAConditionModelImpl.
					getWorkflowMetricsSLADefinitionId()
			};

			finderCache.removeResult(_finderPathCountByC_WMSLADI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_WMSLADI, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((workflowMetricsSLAConditionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLAConditionModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					workflowMetricsSLAConditionModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((workflowMetricsSLAConditionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLAConditionModelImpl.getOriginalUuid(),
					workflowMetricsSLAConditionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					workflowMetricsSLAConditionModelImpl.getUuid(),
					workflowMetricsSLAConditionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((workflowMetricsSLAConditionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_WMSLADI.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					workflowMetricsSLAConditionModelImpl.getOriginalCompanyId(),
					workflowMetricsSLAConditionModelImpl.
						getOriginalWorkflowMetricsSLADefinitionId()
				};

				finderCache.removeResult(_finderPathCountByC_WMSLADI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_WMSLADI, args);

				args = new Object[] {
					workflowMetricsSLAConditionModelImpl.getCompanyId(),
					workflowMetricsSLAConditionModelImpl.
						getWorkflowMetricsSLADefinitionId()
				};

				finderCache.removeResult(_finderPathCountByC_WMSLADI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_WMSLADI, args);
			}
		}

		entityCache.putResult(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			workflowMetricsSLACondition.getPrimaryKey(),
			workflowMetricsSLACondition, false);

		clearUniqueFindersCache(workflowMetricsSLAConditionModelImpl, false);
		cacheUniqueFindersCache(workflowMetricsSLAConditionModelImpl);

		workflowMetricsSLACondition.resetOriginalValues();

		return workflowMetricsSLACondition;
	}

	/**
	 * Returns the workflow metrics sla condition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSLAConditionException {

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			fetchByPrimaryKey(primaryKey);

		if (workflowMetricsSLACondition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSLAConditionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return workflowMetricsSLACondition;
	}

	/**
	 * Returns the workflow metrics sla condition with the primary key or throws a <code>NoSuchSLAConditionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition findByPrimaryKey(
			long workflowMetricsSLAConditionId)
		throws NoSuchSLAConditionException {

		return findByPrimaryKey((Serializable)workflowMetricsSLAConditionId);
	}

	/**
	 * Returns the workflow metrics sla condition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition, or <code>null</code> if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition fetchByPrimaryKey(
		long workflowMetricsSLAConditionId) {

		return fetchByPrimaryKey((Serializable)workflowMetricsSLAConditionId);
	}

	/**
	 * Returns all the workflow metrics sla conditions.
	 *
	 * @return the workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla conditions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @return the range of workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla conditions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
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

		List<WorkflowMetricsSLACondition> list = null;

		if (retrieveFromCache) {
			list = (List<WorkflowMetricsSLACondition>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_WORKFLOWMETRICSSLACONDITION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WORKFLOWMETRICSSLACONDITION;

				if (pagination) {
					sql = sql.concat(
						WorkflowMetricsSLAConditionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLACondition>)QueryUtil.list(
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
	 * Removes all the workflow metrics sla conditions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WorkflowMetricsSLACondition workflowMetricsSLACondition :
				findAll()) {

			remove(workflowMetricsSLACondition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla conditions.
	 *
	 * @return the number of workflow metrics sla conditions
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
					_SQL_COUNT_WORKFLOWMETRICSSLACONDITION);

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
		return "workflowMetricsSLAConditionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WORKFLOWMETRICSSLACONDITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WorkflowMetricsSLAConditionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the workflow metrics sla condition persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			WorkflowMetricsSLAConditionModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLAConditionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLAConditionModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLAConditionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLAConditionModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_WMSLADI = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_WMSLADI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_WMSLADI = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLAConditionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_WMSLADI",
			new String[] {Long.class.getName(), Long.class.getName()},
			WorkflowMetricsSLAConditionModelImpl.COMPANYID_COLUMN_BITMASK |
			WorkflowMetricsSLAConditionModelImpl.
				WORKFLOWMETRICSSLADEFINITIONID_COLUMN_BITMASK);

		_finderPathCountByC_WMSLADI = new FinderPath(
			WorkflowMetricsSLAConditionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLAConditionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_WMSLADI",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(
			WorkflowMetricsSLAConditionImpl.class.getName());
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

	private static final String _SQL_SELECT_WORKFLOWMETRICSSLACONDITION =
		"SELECT workflowMetricsSLACondition FROM WorkflowMetricsSLACondition workflowMetricsSLACondition";

	private static final String _SQL_SELECT_WORKFLOWMETRICSSLACONDITION_WHERE =
		"SELECT workflowMetricsSLACondition FROM WorkflowMetricsSLACondition workflowMetricsSLACondition WHERE ";

	private static final String _SQL_COUNT_WORKFLOWMETRICSSLACONDITION =
		"SELECT COUNT(workflowMetricsSLACondition) FROM WorkflowMetricsSLACondition workflowMetricsSLACondition";

	private static final String _SQL_COUNT_WORKFLOWMETRICSSLACONDITION_WHERE =
		"SELECT COUNT(workflowMetricsSLACondition) FROM WorkflowMetricsSLACondition workflowMetricsSLACondition WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"workflowMetricsSLACondition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WorkflowMetricsSLACondition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WorkflowMetricsSLACondition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsSLAConditionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}