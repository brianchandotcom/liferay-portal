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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionTable;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionImpl;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionModelImpl;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionPersistence;
import com.liferay.portal.workflow.metrics.service.persistence.impl.constants.WorkflowMetricsPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the workflow metrics sla definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		WorkflowMetricsSLADefinitionPersistence.class, BasePersistence.class
	}
)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSLADefinitionException(sb.toString());
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
				_finderPathFetchByUUID_G, finderArgs);
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
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<WorkflowMetricsSLADefinition> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"workflowMetricsSLADefinition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(workflowMetricsSLADefinition.uuid IS NULL OR workflowMetricsSLADefinition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ?";

	private FinderPath _finderPathFetchByWMSLAD_A;
	private FinderPath _finderPathCountByWMSLAD_A;

	/**
	 * Returns the workflow metrics sla definition where workflowMetricsSLADefinitionId = &#63; and active = &#63; or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param active the active
	 * @return the matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByWMSLAD_A(
			long workflowMetricsSLADefinitionId, boolean active)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByWMSLAD_A(workflowMetricsSLADefinitionId, active);

		if (workflowMetricsSLADefinition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("workflowMetricsSLADefinitionId=");
			sb.append(workflowMetricsSLADefinitionId);

			sb.append(", active=");
			sb.append(active);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSLADefinitionException(sb.toString());
		}

		return workflowMetricsSLADefinition;
	}

	/**
	 * Returns the workflow metrics sla definition where workflowMetricsSLADefinitionId = &#63; and active = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param active the active
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByWMSLAD_A(
		long workflowMetricsSLADefinitionId, boolean active) {

		return fetchByWMSLAD_A(workflowMetricsSLADefinitionId, active, true);
	}

	/**
	 * Returns the workflow metrics sla definition where workflowMetricsSLADefinitionId = &#63; and active = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param active the active
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByWMSLAD_A(
		long workflowMetricsSLADefinitionId, boolean active,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {workflowMetricsSLADefinitionId, active};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByWMSLAD_A, finderArgs);
		}

		if (result instanceof WorkflowMetricsSLADefinition) {
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
				(WorkflowMetricsSLADefinition)result;

			if ((workflowMetricsSLADefinitionId !=
					workflowMetricsSLADefinition.
						getWorkflowMetricsSLADefinitionId()) ||
				(active != workflowMetricsSLADefinition.isActive())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_WMSLAD_A_WORKFLOWMETRICSSLADEFINITIONID_2);

			sb.append(_FINDER_COLUMN_WMSLAD_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(workflowMetricsSLADefinitionId);

				queryPos.add(active);

				List<WorkflowMetricsSLADefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByWMSLAD_A, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									workflowMetricsSLADefinitionId, active
								};
							}

							_log.warn(
								"WorkflowMetricsSLADefinitionPersistenceImpl.fetchByWMSLAD_A(long, boolean, boolean) with parameters (" +
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
			return (WorkflowMetricsSLADefinition)result;
		}
	}

	/**
	 * Removes the workflow metrics sla definition where workflowMetricsSLADefinitionId = &#63; and active = &#63; from the database.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param active the active
	 * @return the workflow metrics sla definition that was removed
	 */
	@Override
	public WorkflowMetricsSLADefinition removeByWMSLAD_A(
			long workflowMetricsSLADefinitionId, boolean active)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByWMSLAD_A(workflowMetricsSLADefinitionId, active);

		return remove(workflowMetricsSLADefinition);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where workflowMetricsSLADefinitionId = &#63; and active = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param active the active
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByWMSLAD_A(
		long workflowMetricsSLADefinitionId, boolean active) {

		FinderPath finderPath = _finderPathCountByWMSLAD_A;

		Object[] finderArgs = new Object[] {
			workflowMetricsSLADefinitionId, active
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_WMSLAD_A_WORKFLOWMETRICSSLADEFINITIONID_2);

			sb.append(_FINDER_COLUMN_WMSLAD_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(workflowMetricsSLADefinitionId);

				queryPos.add(active);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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
		_FINDER_COLUMN_WMSLAD_A_WORKFLOWMETRICSSLADEFINITIONID_2 =
			"workflowMetricsSLADefinition.workflowMetricsSLADefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_WMSLAD_A_ACTIVE_2 =
		"workflowMetricsSLADefinition.active = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_S(
		long companyId, int status) {

		return findByC_S(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_S(
		long companyId, int status, int start, int end) {

		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_S(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, status, start, end, orderByComparator
			};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(status != workflowMetricsSLADefinition.getStatus())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_S_First(
			long companyId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_S_First(companyId, status, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_S_First(
		long companyId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_S(
			companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_S_Last(
			long companyId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_S_Last(companyId, status, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_S_Last(
		long companyId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_S(
			companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, status,
				orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, status,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLADefinition getByC_S_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_S(
					companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"workflowMetricsSLADefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_P;
	private FinderPath _finderPathWithoutPaginationFindByC_A_P;
	private FinderPath _finderPathCountByC_A_P;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P(
		long companyId, boolean active, long processId) {

		return findByC_A_P(
			companyId, active, processId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P(
		long companyId, boolean active, long processId, int start, int end) {

		return findByC_A_P(companyId, active, processId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P(
		long companyId, boolean active, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_A_P(
			companyId, active, processId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P(
		long companyId, boolean active, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A_P;
				finderArgs = new Object[] {companyId, active, processId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A_P;
			finderArgs = new Object[] {
				companyId, active, processId, start, end, orderByComparator
			};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(active != workflowMetricsSLADefinition.isActive()) ||
						(processId !=
							workflowMetricsSLADefinition.getProcessId())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_P_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_P_PROCESSID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(processId);

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_P_First(
			long companyId, boolean active, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_P_First(companyId, active, processId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", processId=");
		sb.append(processId);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_P_First(
		long companyId, boolean active, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_A_P(
			companyId, active, processId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_P_Last(
			long companyId, boolean active, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_P_Last(companyId, active, processId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", processId=");
		sb.append(processId);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_P_Last(
		long companyId, boolean active, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_A_P(companyId, active, processId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_A_P(
			companyId, active, processId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_A_P_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, boolean active,
			long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByC_A_P_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active,
				processId, orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_A_P_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active,
				processId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLADefinition getByC_A_P_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, boolean active, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_P_ACTIVE_2);

		sb.append(_FINDER_COLUMN_C_A_P_PROCESSID_2);

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
			sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		queryPos.add(processId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 */
	@Override
	public void removeByC_A_P(long companyId, boolean active, long processId) {
		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_A_P(
					companyId, active, processId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_A_P(long companyId, boolean active, long processId) {
		FinderPath finderPath = _finderPathCountByC_A_P;

		Object[] finderArgs = new Object[] {companyId, active, processId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_P_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_P_PROCESSID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(processId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_A_P_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_ACTIVE_2 =
		"workflowMetricsSLADefinition.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_N_P;
	private FinderPath _finderPathWithoutPaginationFindByC_A_N_P;
	private FinderPath _finderPathCountByC_A_N_P;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_N_P(
		long companyId, boolean active, String name, long processId) {

		return findByC_A_N_P(
			companyId, active, name, processId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_N_P(
		long companyId, boolean active, String name, long processId, int start,
		int end) {

		return findByC_A_N_P(
			companyId, active, name, processId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_N_P(
		long companyId, boolean active, String name, long processId, int start,
		int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_A_N_P(
			companyId, active, name, processId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_N_P(
		long companyId, boolean active, String name, long processId, int start,
		int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A_N_P;
				finderArgs = new Object[] {companyId, active, name, processId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A_N_P;
			finderArgs = new Object[] {
				companyId, active, name, processId, start, end,
				orderByComparator
			};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(active != workflowMetricsSLADefinition.isActive()) ||
						!name.equals(workflowMetricsSLADefinition.getName()) ||
						(processId !=
							workflowMetricsSLADefinition.getProcessId())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_N_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_N_P_ACTIVE_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_N_P_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_A_N_P_NAME_2);
			}

			sb.append(_FINDER_COLUMN_C_A_N_P_PROCESSID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(processId);

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_N_P_First(
			long companyId, boolean active, String name, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_N_P_First(
				companyId, active, name, processId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", name=");
		sb.append(name);

		sb.append(", processId=");
		sb.append(processId);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_N_P_First(
		long companyId, boolean active, String name, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_A_N_P(
			companyId, active, name, processId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_N_P_Last(
			long companyId, boolean active, String name, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_N_P_Last(
				companyId, active, name, processId, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", name=");
		sb.append(name);

		sb.append(", processId=");
		sb.append(processId);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_N_P_Last(
		long companyId, boolean active, String name, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_A_N_P(companyId, active, name, processId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_A_N_P(
			companyId, active, name, processId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_A_N_P_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, boolean active,
			String name, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		name = Objects.toString(name, "");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByC_A_N_P_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active, name,
				processId, orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_A_N_P_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active, name,
				processId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLADefinition getByC_A_N_P_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, boolean active, String name, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_N_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_N_P_ACTIVE_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_N_P_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_A_N_P_NAME_2);
		}

		sb.append(_FINDER_COLUMN_C_A_N_P_PROCESSID_2);

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
			sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(processId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 */
	@Override
	public void removeByC_A_N_P(
		long companyId, boolean active, String name, long processId) {

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_A_N_P(
					companyId, active, name, processId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and active = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param name the name
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_A_N_P(
		long companyId, boolean active, String name, long processId) {

		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_A_N_P;

		Object[] finderArgs = new Object[] {companyId, active, name, processId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_N_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_N_P_ACTIVE_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_N_P_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_A_N_P_NAME_2);
			}

			sb.append(_FINDER_COLUMN_C_A_N_P_PROCESSID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(processId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_A_N_P_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_N_P_ACTIVE_2 =
		"workflowMetricsSLADefinition.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_N_P_NAME_2 =
		"workflowMetricsSLADefinition.name = ? AND ";

	private static final String _FINDER_COLUMN_C_A_N_P_NAME_3 =
		"(workflowMetricsSLADefinition.name IS NULL OR workflowMetricsSLADefinition.name = '') AND ";

	private static final String _FINDER_COLUMN_C_A_N_P_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_P_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_P_S;
	private FinderPath _finderPathCountByC_A_P_S;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P_S(
		long companyId, boolean active, long processId, int status) {

		return findByC_A_P_S(
			companyId, active, processId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P_S(
		long companyId, boolean active, long processId, int status, int start,
		int end) {

		return findByC_A_P_S(
			companyId, active, processId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P_S(
		long companyId, boolean active, long processId, int status, int start,
		int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_A_P_S(
			companyId, active, processId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P_S(
		long companyId, boolean active, long processId, int status, int start,
		int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A_P_S;
				finderArgs = new Object[] {
					companyId, active, processId, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A_P_S;
			finderArgs = new Object[] {
				companyId, active, processId, status, start, end,
				orderByComparator
			};
		}

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(active != workflowMetricsSLADefinition.isActive()) ||
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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_P_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_P_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_P_S_PROCESSID_2);

			sb.append(_FINDER_COLUMN_C_A_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(processId);

				queryPos.add(status);

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_P_S_First(
			long companyId, boolean active, long processId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_P_S_First(
				companyId, active, processId, status, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", processId=");
		sb.append(processId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_P_S_First(
		long companyId, boolean active, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_A_P_S(
			companyId, active, processId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_P_S_Last(
			long companyId, boolean active, long processId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_P_S_Last(
				companyId, active, processId, status, orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", processId=");
		sb.append(processId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_P_S_Last(
		long companyId, boolean active, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_A_P_S(companyId, active, processId, status);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_A_P_S(
			companyId, active, processId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_A_P_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, boolean active,
			long processId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			findByPrimaryKey(workflowMetricsSLADefinitionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinition[] array =
				new WorkflowMetricsSLADefinitionImpl[3];

			array[0] = getByC_A_P_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active,
				processId, status, orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_A_P_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active,
				processId, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLADefinition getByC_A_P_S_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, boolean active, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_P_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_P_S_ACTIVE_2);

		sb.append(_FINDER_COLUMN_C_A_P_S_PROCESSID_2);

		sb.append(_FINDER_COLUMN_C_A_P_S_STATUS_2);

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
			sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		queryPos.add(processId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 */
	@Override
	public void removeByC_A_P_S(
		long companyId, boolean active, long processId, int status) {

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_A_P_S(
					companyId, active, processId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_A_P_S(
		long companyId, boolean active, long processId, int status) {

		FinderPath finderPath = _finderPathCountByC_A_P_S;

		Object[] finderArgs = new Object[] {
			companyId, active, processId, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_P_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_P_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_P_S_PROCESSID_2);

			sb.append(_FINDER_COLUMN_C_A_P_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(processId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_A_P_S_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_S_ACTIVE_2 =
		"workflowMetricsSLADefinition.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_S_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_S_STATUS_2 =
		"workflowMetricsSLADefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_P_NotPV_S;
	private FinderPath _finderPathWithPaginationCountByC_A_P_NotPV_S;

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P_NotPV_S(
		long companyId, boolean active, long processId, String processVersion,
		int status) {

		return findByC_A_P_NotPV_S(
			companyId, active, processId, processVersion, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P_NotPV_S(
		long companyId, boolean active, long processId, String processVersion,
		int status, int start, int end) {

		return findByC_A_P_NotPV_S(
			companyId, active, processId, processVersion, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definitions
	 */
	@Override
	public List<WorkflowMetricsSLADefinition> findByC_A_P_NotPV_S(
		long companyId, boolean active, long processId, String processVersion,
		int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return findByC_A_P_NotPV_S(
			companyId, active, processId, processVersion, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
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
	public List<WorkflowMetricsSLADefinition> findByC_A_P_NotPV_S(
		long companyId, boolean active, long processId, String processVersion,
		int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		processVersion = Objects.toString(processVersion, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_A_P_NotPV_S;
		finderArgs = new Object[] {
			companyId, active, processId, processVersion, status, start, end,
			orderByComparator
		};

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
						list) {

					if ((companyId !=
							workflowMetricsSLADefinition.getCompanyId()) ||
						(active != workflowMetricsSLADefinition.isActive()) ||
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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSID_2);

			boolean bindProcessVersion = false;

			if (processVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_3);
			}
			else {
				bindProcessVersion = true;

				sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_2);
			}

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(processId);

				if (bindProcessVersion) {
					queryPos.add(processVersion);
				}

				queryPos.add(status);

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_P_NotPV_S_First(
			long companyId, boolean active, long processId,
			String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_P_NotPV_S_First(
				companyId, active, processId, processVersion, status,
				orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", processId=");
		sb.append(processId);

		sb.append(", processVersion!=");
		sb.append(processVersion);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_P_NotPV_S_First(
		long companyId, boolean active, long processId, String processVersion,
		int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		List<WorkflowMetricsSLADefinition> list = findByC_A_P_NotPV_S(
			companyId, active, processId, processVersion, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition findByC_A_P_NotPV_S_Last(
			long companyId, boolean active, long processId,
			String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			fetchByC_A_P_NotPV_S_Last(
				companyId, active, processId, processVersion, status,
				orderByComparator);

		if (workflowMetricsSLADefinition != null) {
			return workflowMetricsSLADefinition;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", processId=");
		sb.append(processId);

		sb.append(", processVersion!=");
		sb.append(processVersion);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchSLADefinitionException(sb.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition fetchByC_A_P_NotPV_S_Last(
		long companyId, boolean active, long processId, String processVersion,
		int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		int count = countByC_A_P_NotPV_S(
			companyId, active, processId, processVersion, status);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinition> list = findByC_A_P_NotPV_S(
			companyId, active, processId, processVersion, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinition[] findByC_A_P_NotPV_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, boolean active,
			long processId, String processVersion, int status,
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

			array[0] = getByC_A_P_NotPV_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active,
				processId, processVersion, status, orderByComparator, true);

			array[1] = workflowMetricsSLADefinition;

			array[2] = getByC_A_P_NotPV_S_PrevAndNext(
				session, workflowMetricsSLADefinition, companyId, active,
				processId, processVersion, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WorkflowMetricsSLADefinition getByC_A_P_NotPV_S_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		long companyId, boolean active, long processId, String processVersion,
		int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_ACTIVE_2);

		sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSID_2);

		boolean bindProcessVersion = false;

		if (processVersion.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_3);
		}
		else {
			bindProcessVersion = true;

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_2);
		}

		sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_STATUS_2);

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
			sb.append(WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		queryPos.add(processId);

		if (bindProcessVersion) {
			queryPos.add(processVersion);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 */
	@Override
	public void removeByC_A_P_NotPV_S(
		long companyId, boolean active, long processId, String processVersion,
		int status) {

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				findByC_A_P_NotPV_S(
					companyId, active, processId, processVersion, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinition);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and active = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	@Override
	public int countByC_A_P_NotPV_S(
		long companyId, boolean active, long processId, String processVersion,
		int status) {

		processVersion = Objects.toString(processVersion, "");

		FinderPath finderPath = _finderPathWithPaginationCountByC_A_P_NotPV_S;

		Object[] finderArgs = new Object[] {
			companyId, active, processId, processVersion, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSID_2);

			boolean bindProcessVersion = false;

			if (processVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_3);
			}
			else {
				bindProcessVersion = true;

				sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_2);
			}

			sb.append(_FINDER_COLUMN_C_A_P_NOTPV_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(processId);

				if (bindProcessVersion) {
					queryPos.add(processVersion);
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_A_P_NOTPV_S_COMPANYID_2 =
		"workflowMetricsSLADefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_NOTPV_S_ACTIVE_2 =
		"workflowMetricsSLADefinition.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSID_2 =
		"workflowMetricsSLADefinition.processId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_2 =
		"workflowMetricsSLADefinition.processVersion != ? AND ";

	private static final String _FINDER_COLUMN_C_A_P_NOTPV_S_PROCESSVERSION_3 =
		"(workflowMetricsSLADefinition.processVersion IS NULL OR workflowMetricsSLADefinition.processVersion != '') AND ";

	private static final String _FINDER_COLUMN_C_A_P_NOTPV_S_STATUS_2 =
		"workflowMetricsSLADefinition.status = ?";

	public WorkflowMetricsSLADefinitionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"workflowMetricsSLADefinitionId", "wmSLADefinitionId");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(WorkflowMetricsSLADefinition.class);

		setModelImplClass(WorkflowMetricsSLADefinitionImpl.class);
		setModelPKClass(long.class);

		setTable(WorkflowMetricsSLADefinitionTable.INSTANCE);
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
			_finderPathFetchByWMSLAD_A,
			new Object[] {
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId(),
				workflowMetricsSLADefinition.isActive()
			},
			workflowMetricsSLADefinition);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the workflow metrics sla definitions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitions the workflow metrics sla definitions
	 */
	@Override
	public void cacheResult(
		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (workflowMetricsSLADefinitions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				workflowMetricsSLADefinitions) {

			if (entityCache.getResult(
					WorkflowMetricsSLADefinitionImpl.class,
					workflowMetricsSLADefinition.getPrimaryKey()) == null) {

				cacheResult(workflowMetricsSLADefinition);
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

		finderCache.clearCache(WorkflowMetricsSLADefinitionImpl.class);
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
			WorkflowMetricsSLADefinitionImpl.class,
			workflowMetricsSLADefinition);
	}

	@Override
	public void clearCache(
		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions) {

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				workflowMetricsSLADefinitions) {

			entityCache.removeResult(
				WorkflowMetricsSLADefinitionImpl.class,
				workflowMetricsSLADefinition);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(WorkflowMetricsSLADefinitionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				WorkflowMetricsSLADefinitionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		WorkflowMetricsSLADefinitionModelImpl
			workflowMetricsSLADefinitionModelImpl) {

		Object[] args = new Object[] {
			workflowMetricsSLADefinitionModelImpl.getUuid(),
			workflowMetricsSLADefinitionModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			workflowMetricsSLADefinitionModelImpl);

		args = new Object[] {
			workflowMetricsSLADefinitionModelImpl.
				getWorkflowMetricsSLADefinitionId(),
			workflowMetricsSLADefinitionModelImpl.isActive()
		};

		finderCache.putResult(
			_finderPathCountByWMSLAD_A, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByWMSLAD_A, args,
			workflowMetricsSLADefinitionModelImpl);
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
		catch (NoSuchSLADefinitionException noSuchEntityException) {
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
		catch (Exception exception) {
			throw processException(exception);
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

		Date date = new Date();

		if (isNew && (workflowMetricsSLADefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				workflowMetricsSLADefinition.setCreateDate(date);
			}
			else {
				workflowMetricsSLADefinition.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!workflowMetricsSLADefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				workflowMetricsSLADefinition.setModifiedDate(date);
			}
			else {
				workflowMetricsSLADefinition.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(workflowMetricsSLADefinition);
			}
			else {
				workflowMetricsSLADefinition =
					(WorkflowMetricsSLADefinition)session.merge(
						workflowMetricsSLADefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			WorkflowMetricsSLADefinitionImpl.class,
			workflowMetricsSLADefinitionModelImpl, false, true);

		cacheUniqueFindersCache(workflowMetricsSLADefinitionModelImpl);

		if (isNew) {
			workflowMetricsSLADefinition.setNew(false);
		}

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionModelImpl</code>.
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

		List<WorkflowMetricsSLADefinition> list = null;

		if (useFinderCache) {
			list = (List<WorkflowMetricsSLADefinition>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_WORKFLOWMETRICSSLADEFINITION;

				sql = sql.concat(
					WorkflowMetricsSLADefinitionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<WorkflowMetricsSLADefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_WORKFLOWMETRICSSLADEFINITION);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "wmSLADefinitionId";
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
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathFetchByWMSLAD_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByWMSLAD_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"wmSLADefinitionId", "active_"}, true);

		_finderPathCountByWMSLAD_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByWMSLAD_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"wmSLADefinitionId", "active_"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_finderPathWithPaginationFindByC_A_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "processId"}, true);

		_finderPathWithoutPaginationFindByC_A_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			new String[] {"companyId", "active_", "processId"}, true);

		_finderPathCountByC_A_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			new String[] {"companyId", "active_", "processId"}, false);

		_finderPathWithPaginationFindByC_A_N_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_N_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "name", "processId"}, true);

		_finderPathWithoutPaginationFindByC_A_N_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_N_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "active_", "name", "processId"}, true);

		_finderPathCountByC_A_N_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_N_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "active_", "name", "processId"}, false);

		_finderPathWithPaginationFindByC_A_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "processId", "status"}, true);

		_finderPathWithoutPaginationFindByC_A_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "active_", "processId", "status"}, true);

		_finderPathCountByC_A_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "active_", "processId", "status"},
			false);

		_finderPathWithPaginationFindByC_A_P_NotPV_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_P_NotPV_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "active_", "processId", "processVersion", "status"
			},
			true);

		_finderPathWithPaginationCountByC_A_P_NotPV_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_A_P_NotPV_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"companyId", "active_", "processId", "processVersion", "status"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			WorkflowMetricsSLADefinitionImpl.class.getName());
	}

	@Override
	@Reference(
		target = WorkflowMetricsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = WorkflowMetricsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = WorkflowMetricsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
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
		new String[] {"uuid", "workflowMetricsSLADefinitionId", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private WorkflowMetricsSLADefinitionModelArgumentsResolver
		_workflowMetricsSLADefinitionModelArgumentsResolver;

}