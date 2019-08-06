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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionImpl;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionModelImpl;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence implementation for the workflow metrics sla definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLADefinitionPersistenceImpl
	extends BasePersistenceImpl<WorkflowMetricsSLADefinition>
	implements WorkflowMetricsSLADefinitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WorkflowMetricsSLADefinitionUtil</code> to access the workflow metrics sla definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WorkflowMetricsSLADefinitionImpl.class.getName();

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
	 * Returns all the workflow metrics sla definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if (!uuid.equals(workflowMetricsSLADefinition.getUuid())) {
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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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
					WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
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
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByUuid_First(uuid, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByUuid_Last(uuid, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByUuid_PrevAndNext(
			long workflowMetricsSLADefinitionId, String uuid,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, workflowMetricsSLADefinition, uuid, orderByComparator,
				true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByUuid_PrevAndNext(
				session, workflowMetricsSLADefinition, uuid, orderByComparator,
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

	protected WorkflowMetricsSLADefinition getByUuid_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition, String uuid,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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
			query.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLADefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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
		"workflowMetricsSLADefinition.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(workflowMetricsSLADefinition.uuid IS NULL OR workflowMetricsSLADefinition.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByUUID_G(String uuid, long groupId)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByUUID_G(uuid, groupId);

		if (workflowMetricsSLADefinition == null) {
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

			throw new NoSuchSLADefinitionException(msg.toString());
		}

		return workflowMetricsSLADefinition;
	}

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByUUID_G(
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

		if (result instanceof WorkflowMetricsSLADefinition) {
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
				(WorkflowMetricsSLADefinition)result;

			if (!Objects.equals(uuid, workflowMetricsSLADefinition.getUuid()) ||
				(groupId != workflowMetricsSLADefinition.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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

				List<WorkflowMetricsSLADefinition> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
						list.get(0);

					result = workflowMetricsSLADefinition;

					cacheResult(workflowMetricsSLADefinition);
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
			return (WorkflowMetricsSLADefinition)result;
		}
	}

	/**
	 * Removes the workflow metrics sla definition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla definition that was removed
	 */
	@Override
	public WorkflowMetricsSLADefinition removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByUUID_G(uuid, groupId);

		return remove(workflowMetricsSLADefinition);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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
		"workflowMetricsSLADefinition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(workflowMetricsSLADefinition.uuid IS NULL OR workflowMetricsSLADefinition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"workflowMetricsSLADefinition.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

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

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if (!uuid.equals(workflowMetricsSLADefinition.getUuid()) ||
						(companyId !=
							workflowMetricsSLADefinition.getCompanyId())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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
					WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
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
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLADefinitionId, String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLADefinition, uuid, companyId,
				orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLADefinition, uuid, companyId,
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

	protected WorkflowMetricsSLADefinition getByUuid_C_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition, String uuid,
		long companyId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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
			query.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLADefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

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
		"workflowMetricsSLADefinition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(workflowMetricsSLADefinition.uuid IS NULL OR workflowMetricsSLADefinition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_P;
	private FinderPath _finderPathWithoutPaginationFindByC_P;
	private FinderPath _finderPathCountByC_P;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId) {

		return findByC_P(
			companyId, processId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end) {

		return findByC_P(companyId, processId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_P(
			companyId, processId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_P;
				finderArgs = new Object[] {companyId, processId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_P;
			finderArgs = new Object[] {
				companyId, processId, start, end, orderByComparator
			};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(processId !=
							workflowMetricsSLADefinition.getProcessId())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PROCESSID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(processId);

				if (!pagination) {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_P_First(
			long companyId, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_P_First(companyId, processId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", processId=");
		msg.append(processId);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_P_First(
		long companyId, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_P(
			companyId, processId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_P_Last(
			long companyId, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_P_Last(companyId, processId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", processId=");
		msg.append(processId);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_P_Last(
		long companyId, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_P(companyId, processId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_P(
			companyId, processId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_P_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByC_P_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, processId,
				orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_P_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, processId,
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

	protected WorkflowMetricsSLADefinition getByC_P_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PROCESSID_2);

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
			query.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(processId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 */
	@Override
	public void removeByC_P(long companyId, long processId) {
		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_P(
					companyId, processId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_P(long companyId, long processId) {
		FinderPath finderPath = _finderPathCountByC_P;

		Object[] finderArgs = new Object[] {companyId, processId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PROCESSID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(processId);

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

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ?";

	private FinderPath _finderPathFetchByC_N_P;
	private FinderPath _finderPathCountByC_N_P;

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_N_P(
			long companyId, String name, long processId)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_N_P(companyId, name, processId);

		if (workflowMetricsSLADefinition == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", processId=");
			msg.append(processId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSLADefinitionException(msg.toString());
		}

		return workflowMetricsSLADefinition;
	}

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_N_P(
		long companyId, String name, long processId) {

		return fetchByC_N_P(companyId, name, processId, true);
	}

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_N_P(
		long companyId, String name, long processId, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name, processId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N_P, finderArgs, this);
		}

		if (result instanceof WorkflowMetricsSLADefinition) {
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
				(WorkflowMetricsSLADefinition)result;

			if ((companyId != workflowMetricsSLADefinition.getCompanyId()) ||
				!Objects.equals(name, workflowMetricsSLADefinition.getName()) ||
				(processId != workflowMetricsSLADefinition.getProcessId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_N_P_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_P_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_P_PROCESSID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(processId);

				List<WorkflowMetricsSLADefinition> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N_P, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, name, processId
								};
							}

							_log.warn(
								"WorkflowMetricsSLADefinitionPersistenceImpl.fetchByC_N_P(long, String, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
						list.get(0);

					result = workflowMetricsSLADefinition;

					cacheResult(workflowMetricsSLADefinition);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByC_N_P, finderArgs);
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
			return (WorkflowMetricsSLADefinition)result;
		}
	}

	/**
	 * Removes the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the workflow metrics sla definition that was removed
	 */
	@Override
	public WorkflowMetricsSLADefinition removeByC_N_P(
			long companyId, String name, long processId)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition = findByC_N_P(
			companyId, name, processId);

		return remove(workflowMetricsSLADefinition);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_N_P(long companyId, String name, long processId) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N_P;

		Object[] finderArgs = new Object[] {companyId, name, processId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_N_P_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_P_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_P_PROCESSID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(processId);

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

	private static final String _FINDER_COLUMN_C_N_P_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_P_NAME_2 =
		"workflowMetricsSLADefinition.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_P_NAME_3 =
		"(workflowMetricsSLADefinition.name IS NULL OR workflowMetricsSLADefinition.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_P_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ?";

	private FinderPath _finderPathWithPaginationFindByC_P_S;
	private FinderPath _finderPathWithoutPaginationFindByC_P_S;
	private FinderPath _finderPathCountByC_P_S;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status) {

		return findByC_P_S(
			companyId, processId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end) {

		return findByC_P_S(companyId, processId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_P_S(
			companyId, processId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_P_S;
				finderArgs = new Object[] {companyId, processId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_P_S;
			finderArgs = new Object[] {
				companyId, processId, status, start, end, orderByComparator
			};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(processId !=
							workflowMetricsSLADefinition.getProcessId()) ||
						(status != workflowMetricsSLADefinition.getStatus())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_S_PROCESSID_2);

			query.append(_FINDER_COLUMN_C_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(processId);

				qPos.add(status);

				if (!pagination) {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_P_S_First(
			long companyId, long processId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_P_S_First(companyId, processId, status, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", processId=");
		msg.append(processId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_P_S_First(
		long companyId, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_P_S(
			companyId, processId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_P_S_Last(
			long companyId, long processId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_P_S_Last(companyId, processId, status, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", processId=");
		msg.append(processId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_P_S_Last(
		long companyId, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_P_S(companyId, processId, status);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_P_S(
			companyId, processId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_P_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByC_P_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, processId,
				status, orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_P_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, processId,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLADefinition getByC_P_S_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_S_PROCESSID_2);

		query.append(_FINDER_COLUMN_C_P_S_STATUS_2);

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
			query.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(processId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 */
	@Override
	public void removeByC_P_S(long companyId, long processId, int status) {
		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_P_S(
					companyId, processId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_P_S(long companyId, long processId, int status) {
		FinderPath finderPath = _finderPathCountByC_P_S;

		Object[] finderArgs = new Object[] {companyId, processId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_S_PROCESSID_2);

			query.append(_FINDER_COLUMN_C_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(processId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_P_S_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_S_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_S_STATUS_2 =
		"workflowMetricsSLADefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_P_NotPV_S;
	private FinderPath _finderPathWithPaginationCountByC_P_NotPV_S;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status) {

		return findByC_P_NotPV_S(
			companyId, processId, processVersion, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end) {

		return findByC_P_NotPV_S(
			companyId, processId, processVersion, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_P_NotPV_S(
			companyId, processId, processVersion, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		processVersion = Objects.toString(processVersion, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_P_NotPV_S;
		finderArgs = new Object[] {
			companyId, processId, processVersion, status, start, end,
			orderByComparator
		};

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(processId !=
							workflowMetricsSLADefinition.getProcessId()) ||
						processVersion.equals(
							workflowMetricsSLADefinition.getProcessVersion()) ||
						(status != workflowMetricsSLADefinition.getStatus())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_P_NOTPV_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSID_2);

			boolean bindProcessVersion = false;

			if (processVersion.isEmpty()) {
				query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_3);
			}
			else {
				bindProcessVersion = true;

				query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_2);
			}

			query.append(_FINDER_COLUMN_C_P_NOTPV_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(
					WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(processId);

				if (bindProcessVersion) {
					qPos.add(processVersion);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_P_NotPV_S_First(
			long companyId, long processId, String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_P_NotPV_S_First(
				companyId, processId, processVersion, status,
				orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", processId=");
		msg.append(processId);

		msg.append(", processVersion=");
		msg.append(processVersion);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_P_NotPV_S_First(
		long companyId, long processId, String processVersion, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_P_NotPV_S(
			companyId, processId, processVersion, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_P_NotPV_S_Last(
			long companyId, long processId, String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_P_NotPV_S_Last(
				companyId, processId, processVersion, status,
				orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", processId=");
		msg.append(processId);

		msg.append(", processVersion=");
		msg.append(processVersion);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchSLADefinitionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_P_NotPV_S_Last(
		long companyId, long processId, String processVersion, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_P_NotPV_S(
			companyId, processId, processVersion, status);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_P_NotPV_S(
			companyId, processId, processVersion, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_P_NotPV_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		processVersion = Objects.toString(processVersion, "");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByC_P_NotPV_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, processId,
				processVersion, status, orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_P_NotPV_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, processId,
				processVersion, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLADefinition getByC_P_NotPV_S_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, long processId, String processVersion, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_P_NOTPV_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSID_2);

		boolean bindProcessVersion = false;

		if (processVersion.isEmpty()) {
			query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_3);
		}
		else {
			bindProcessVersion = true;

			query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_2);
		}

		query.append(_FINDER_COLUMN_C_P_NOTPV_S_STATUS_2);

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
			query.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(processId);

		if (bindProcessVersion) {
			qPos.add(processVersion);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 */
	@Override
	public void removeByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status) {

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_P_NotPV_S(
					companyId, processId, processVersion, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status) {

		processVersion = Objects.toString(processVersion, "");

		FinderPath finderPath = _finderPathWithPaginationCountByC_P_NotPV_S;

		Object[] finderArgs = new Object[] {
			companyId, processId, processVersion, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_P_NOTPV_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSID_2);

			boolean bindProcessVersion = false;

			if (processVersion.isEmpty()) {
				query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_3);
			}
			else {
				bindProcessVersion = true;

				query.append(_FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_2);
			}

			query.append(_FINDER_COLUMN_C_P_NOTPV_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(processId);

				if (bindProcessVersion) {
					qPos.add(processVersion);
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_P_NOTPV_S_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_NOTPV_S_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_2 =
		"workflowMetricsSLADefinition.processVersion != ? AND ";

	private static final String _FINDER_COLUMN_C_P_NOTPV_S_PROCESSVERSION_3 =
		"(workflowMetricsSLADefinition.processVersion IS NULL OR workflowMetricsSLADefinition.processVersion != '') AND ";

	private static final String _FINDER_COLUMN_C_P_NOTPV_S_STATUS_2 =
		"workflowMetricsSLADefinition.status = ?";

	public WorkflowMetricsSLADefinitionPersistenceImpl() {
		setModelClass(WorkflowMetricsSLADefinition.class);

		setModelImplClass(WorkflowMetricsSLADefinitionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the workflow metrics sla definition in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 */
	@Override
	public void cacheResult(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		entityCache.putResult(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			workflowMetricsSLADefinition.getPrimaryKey(),
			workflowMetricsSLADefinition);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				workflowMetricsSLADefinition.getUuid(),
				workflowMetricsSLADefinition.getGroupId()
			},
			workflowMetricsSLADefinition);

		finderCache.putResult(
			_finderPathFetchByC_N_P,
			new Object[] {
				workflowMetricsSLADefinition.getCompanyId(),
				workflowMetricsSLADefinition.getName(),
				workflowMetricsSLADefinition.getProcessId()
			},
			workflowMetricsSLADefinition);

		workflowMetricsSLADefinition.resetOriginalValues();
	}

	/**
	 * Caches the workflow metrics sla definitions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitions the workflow metrics sla definitions
	 */
	@Override
	public void cacheResult(
		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions) {

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				workflowMetricsSLADefinitions) {

			if (entityCache.getResult(
					WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
					WorkflowMetricsSLADefinitionImpl.class,
					workflowMetricsSLADefinition.getPrimaryKey()) == null) {

				cacheResult(workflowMetricsSLADefinition);
			}
			else {
				workflowMetricsSLADefinition.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all workflow metrics sla definitions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WorkflowMetricsSLADefinitionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the workflow metrics sla definition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		entityCache.removeResult(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			workflowMetricsSLADefinition.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(WorkflowMetricsSLADefinitionModelImpl)workflowMetricsSLADefinition,
			true);
	}

	@Override
	public void clearCache(
		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				workflowMetricsSLADefinitions) {

			entityCache.removeResult(
				WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
				WorkflowMetricsSLADefinitionImpl.class,
				workflowMetricsSLADefinition.getPrimaryKey());

			clearUniqueFindersCache(
				(WorkflowMetricsSLADefinitionModelImpl)
					workflowMetricsSLADefinition,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		WorkflowMetricsSLADefinitionModelImpl
			workflowMetricsSLADefinitionModelImpl) {

		Object[] args = new Object[] {
			workflowMetricsSLADefinitionModelImpl.getUuid(),
			workflowMetricsSLADefinitionModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			workflowMetricsSLADefinitionModelImpl, false);

		args = new Object[] {
			workflowMetricsSLADefinitionModelImpl.getCompanyId(),
			workflowMetricsSLADefinitionModelImpl.getName(),
			workflowMetricsSLADefinitionModelImpl.getProcessId()
		};

		finderCache.putResult(
			_finderPathCountByC_N_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N_P, args,
			workflowMetricsSLADefinitionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		WorkflowMetricsSLADefinitionModelImpl
			workflowMetricsSLADefinitionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getUuid(),
				workflowMetricsSLADefinitionModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((workflowMetricsSLADefinitionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getOriginalUuid(),
				workflowMetricsSLADefinitionModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getCompanyId(),
				workflowMetricsSLADefinitionModelImpl.getName(),
				workflowMetricsSLADefinitionModelImpl.getProcessId()
			};

			finderCache.removeResult(_finderPathCountByC_N_P, args);
			finderCache.removeResult(_finderPathFetchByC_N_P, args);
		}

		if ((workflowMetricsSLADefinitionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getOriginalCompanyId(),
				workflowMetricsSLADefinitionModelImpl.getOriginalName(),
				workflowMetricsSLADefinitionModelImpl.getOriginalProcessId()
			};

			finderCache.removeResult(_finderPathCountByC_N_P, args);
			finderCache.removeResult(_finderPathFetchByC_N_P, args);
		}
	}

	/**
	 * Creates a new workflow metrics sla definition with the primary key. Does not add the workflow metrics sla definition to the database.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key for the new workflow metrics sla definition
	 * @return the new workflow metrics sla definition
	 */
	@Override
	public WorkflowMetricsSLADefinition create(
		long workflowMetricsSLADefinitionId) {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			new WorkflowMetricsSLADefinitionImpl();

		workflowMetricsSLADefinition.setNew(true);
		workflowMetricsSLADefinition.setPrimaryKey(
			workflowMetricsSLADefinitionId);

		String uuid = PortalUUIDUtil.generate();

		workflowMetricsSLADefinition.setUuid(uuid);

		workflowMetricsSLADefinition.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return workflowMetricsSLADefinition;
	}

	/**
	 * Removes the workflow metrics sla definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition remove(
			long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionException {

		return remove((Serializable)workflowMetricsSLADefinitionId);
	}

	/**
	 * Removes the workflow metrics sla definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition remove(Serializable primaryKey)
		throws NoSuchSLADefinitionException {

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
				(WorkflowMetricsSLADefinition)session.get(
					WorkflowMetricsSLADefinitionImpl.class, primaryKey);

			if (workflowMetricsSLADefinition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSLADefinitionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(workflowMetricsSLADefinition);
		}
		catch (NoSuchSLADefinitionException nsee) {
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
	protected WorkflowMetricsSLADefinition removeImpl(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(workflowMetricsSLADefinition)) {
				workflowMetricsSLADefinition =
					(WorkflowMetricsSLADefinition)session.get(
						WorkflowMetricsSLADefinitionImpl.class,
						workflowMetricsSLADefinition.getPrimaryKeyObj());
			}

			if (workflowMetricsSLADefinition != null) {
				session.delete(workflowMetricsSLADefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (workflowMetricsSLADefinition != null) {
			clearCache(workflowMetricsSLADefinition);
		}

		return workflowMetricsSLADefinition;
	}

	@Override
	public WorkflowMetricsSLADefinition updateImpl(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		boolean isNew = workflowMetricsSLADefinition.isNew();

		if (!(workflowMetricsSLADefinition instanceof
				WorkflowMetricsSLADefinitionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					workflowMetricsSLADefinition.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					workflowMetricsSLADefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in workflowMetricsSLADefinition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WorkflowMetricsSLADefinition implementation " +
					workflowMetricsSLADefinition.getClass());
		}

		WorkflowMetricsSLADefinitionModelImpl
			workflowMetricsSLADefinitionModelImpl =
				(WorkflowMetricsSLADefinitionModelImpl)
					workflowMetricsSLADefinition;

		if (Validator.isNull(workflowMetricsSLADefinition.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			workflowMetricsSLADefinition.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (workflowMetricsSLADefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				workflowMetricsSLADefinition.setCreateDate(now);
			}
			else {
				workflowMetricsSLADefinition.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!workflowMetricsSLADefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				workflowMetricsSLADefinition.setModifiedDate(now);
			}
			else {
				workflowMetricsSLADefinition.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (workflowMetricsSLADefinition.isNew()) {
				session.save(workflowMetricsSLADefinition);

				workflowMetricsSLADefinition.setNew(false);
			}
			else {
				workflowMetricsSLADefinition =
					(WorkflowMetricsSLADefinition)session.merge(
						workflowMetricsSLADefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!WorkflowMetricsSLADefinitionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getUuid(),
				workflowMetricsSLADefinitionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getCompanyId(),
				workflowMetricsSLADefinitionModelImpl.getProcessId()
			};

			finderCache.removeResult(_finderPathCountByC_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_P, args);

			args = new Object[] {
				workflowMetricsSLADefinitionModelImpl.getCompanyId(),
				workflowMetricsSLADefinitionModelImpl.getProcessId(),
				workflowMetricsSLADefinitionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByC_P_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_P_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((workflowMetricsSLADefinitionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((workflowMetricsSLADefinitionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.getOriginalUuid(),
					workflowMetricsSLADefinitionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.getUuid(),
					workflowMetricsSLADefinitionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((workflowMetricsSLADefinitionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.
						getOriginalCompanyId(),
					workflowMetricsSLADefinitionModelImpl.getOriginalProcessId()
				};

				finderCache.removeResult(_finderPathCountByC_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_P, args);

				args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.getCompanyId(),
					workflowMetricsSLADefinitionModelImpl.getProcessId()
				};

				finderCache.removeResult(_finderPathCountByC_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_P, args);
			}

			if ((workflowMetricsSLADefinitionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_P_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.
						getOriginalCompanyId(),
					workflowMetricsSLADefinitionModelImpl.
						getOriginalProcessId(),
					workflowMetricsSLADefinitionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByC_P_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_P_S, args);

				args = new Object[] {
					workflowMetricsSLADefinitionModelImpl.getCompanyId(),
					workflowMetricsSLADefinitionModelImpl.getProcessId(),
					workflowMetricsSLADefinitionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByC_P_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_P_S, args);
			}
		}

		entityCache.putResult(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			workflowMetricsSLADefinition.getPrimaryKey(),
			workflowMetricsSLADefinition, false);

		clearUniqueFindersCache(workflowMetricsSLADefinitionModelImpl, false);
		cacheUniqueFindersCache(workflowMetricsSLADefinitionModelImpl);

		workflowMetricsSLADefinition.resetOriginalValues();

		return workflowMetricsSLADefinition;
	}

	/**
	 * Returns the workflow metrics sla definition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByPrimaryKey(primaryKey);

		if (workflowMetricsSLADefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSLADefinitionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return workflowMetricsSLADefinition;
	}

	/**
	 * Returns the workflow metrics sla definition with the primary key or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByPrimaryKey(
			long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionException {

		return findByPrimaryKey((Serializable)workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the workflow metrics sla definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition, or <code>null</code> if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByPrimaryKey(
		long workflowMetricsSLADefinitionId) {

		return fetchByPrimaryKey((Serializable)workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns all the workflow metrics sla definitions.
	 *
	 * @return the workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WORKFLOWMETRICSSLADEFINITION;

				if (pagination) {
					sql = sql.concat(
						WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Removes all the workflow metrics sla definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findAll()) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions.
	 *
	 * @return the number of workflow metrics sla definitions
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
					_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION);

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
		return "workflowMetricsSLADefinitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WORKFLOWMETRICSSLADEFINITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WorkflowMetricsSLADefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the workflow metrics sla definition persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			WorkflowMetricsSLADefinitionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLADefinitionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLADefinitionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_P = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_P = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			WorkflowMetricsSLADefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.PROCESSID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByC_P = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByC_N_P = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			WorkflowMetricsSLADefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.NAME_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.PROCESSID_COLUMN_BITMASK);

		_finderPathCountByC_N_P = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_N_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});

		_finderPathWithPaginationFindByC_P_S = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_P_S = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			WorkflowMetricsSLADefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.PROCESSID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.STATUS_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByC_P_S = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByC_P_NotPV_S = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_NotPV_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_P_NotPV_S = new FinderPath(
			WorkflowMetricsSLADefinitionModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowMetricsSLADefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByC_P_NotPV_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});
	}

	public void destroy() {
		entityCache.removeCache(
			WorkflowMetricsSLADefinitionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_WORKFLOWMETRICSSLADEFINITION =
		"SELECT workflowMetricsSLADefinition FROM WorkflowMetricsSLADefinition workflowMetricsSLADefinition";

	private static final String _SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE =
		"SELECT workflowMetricsSLADefinition FROM WorkflowMetricsSLADefinition workflowMetricsSLADefinition WHERE ";

	private static final String _SQL_COUNT_WORKFLOWMETRICSSLADEFINITION =
		"SELECT COUNT(workflowMetricsSLADefinition) FROM WorkflowMetricsSLADefinition workflowMetricsSLADefinition";

	private static final String _SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE =
		"SELECT COUNT(workflowMetricsSLADefinition) FROM WorkflowMetricsSLADefinition workflowMetricsSLADefinition WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"workflowMetricsSLADefinition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WorkflowMetricsSLADefinition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WorkflowMetricsSLADefinition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsSLADefinitionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}