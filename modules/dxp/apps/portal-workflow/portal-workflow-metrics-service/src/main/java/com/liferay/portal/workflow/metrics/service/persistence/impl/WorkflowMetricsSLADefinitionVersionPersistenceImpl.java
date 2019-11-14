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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionVersionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionImpl;
import com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionModelImpl;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionVersionPersistence;
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
 * The persistence implementation for the workflow metrics sla definition version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = WorkflowMetricsSLADefinitionVersionPersistence.class)
public class WorkflowMetricsSLADefinitionVersionPersistenceImpl
	extends BasePersistenceImpl<WorkflowMetricsSLADefinitionVersion>
	implements WorkflowMetricsSLADefinitionVersionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WorkflowMetricsSLADefinitionVersionUtil</code> to access the workflow metrics sla definition version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WorkflowMetricsSLADefinitionVersionImpl.class.getName();

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
	 * Returns all the workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @return the range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
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

		List<WorkflowMetricsSLADefinitionVersion> list = null;

		if (useFinderCache) {
			list =
				(List<WorkflowMetricsSLADefinitionVersion>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion : list) {

					if (!uuid.equals(
							workflowMetricsSLADefinitionVersion.getUuid())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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
				query.append(
					WorkflowMetricsSLADefinitionVersionModelImpl.ORDER_BY_JPQL);
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

				list =
					(List<WorkflowMetricsSLADefinitionVersion>)QueryUtil.list(
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
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = fetchByUuid_First(
				uuid, orderByComparator);

		if (workflowMetricsSLADefinitionVersion != null) {
			return workflowMetricsSLADefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLADefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		List<WorkflowMetricsSLADefinitionVersion> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = fetchByUuid_Last(
				uuid, orderByComparator);

		if (workflowMetricsSLADefinitionVersion != null) {
			return workflowMetricsSLADefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchSLADefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinitionVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definition versions before and after the current workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the current workflow metrics sla definition version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion[] findByUuid_PrevAndNext(
			long workflowMetricsSLADefinitionVersionId, String uuid,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = findByPrimaryKey(
				workflowMetricsSLADefinitionVersionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinitionVersion[] array =
				new WorkflowMetricsSLADefinitionVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, workflowMetricsSLADefinitionVersion, uuid,
				orderByComparator, true);

			array[1] = workflowMetricsSLADefinitionVersion;

			array[2] = getByUuid_PrevAndNext(
				session, workflowMetricsSLADefinitionVersion, uuid,
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

	protected WorkflowMetricsSLADefinitionVersion getByUuid_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion,
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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
			query.append(
				WorkflowMetricsSLADefinitionVersionModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLADefinitionVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinitionVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definition versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion :
					findByUuid(
						uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinitionVersion);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla definition versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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
		"workflowMetricsSLADefinitionVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(workflowMetricsSLADefinitionVersion.uuid IS NULL OR workflowMetricsSLADefinitionVersion.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByUUID_G(
			String uuid, long groupId)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = fetchByUUID_G(uuid, groupId);

		if (workflowMetricsSLADefinitionVersion == null) {
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

			throw new NoSuchSLADefinitionVersionException(msg.toString());
		}

		return workflowMetricsSLADefinitionVersion;
	}

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByUUID_G(
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

		if (result instanceof WorkflowMetricsSLADefinitionVersion) {
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion =
					(WorkflowMetricsSLADefinitionVersion)result;

			if (!Objects.equals(
					uuid, workflowMetricsSLADefinitionVersion.getUuid()) ||
				(groupId != workflowMetricsSLADefinitionVersion.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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

				List<WorkflowMetricsSLADefinitionVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion = list.get(0);

					result = workflowMetricsSLADefinitionVersion;

					cacheResult(workflowMetricsSLADefinitionVersion);
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
			return (WorkflowMetricsSLADefinitionVersion)result;
		}
	}

	/**
	 * Removes the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla definition version that was removed
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = findByUUID_G(uuid, groupId);

		return remove(workflowMetricsSLADefinitionVersion);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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
		"workflowMetricsSLADefinitionVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(workflowMetricsSLADefinitionVersion.uuid IS NULL OR workflowMetricsSLADefinitionVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"workflowMetricsSLADefinitionVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @return the range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
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

		List<WorkflowMetricsSLADefinitionVersion> list = null;

		if (useFinderCache) {
			list =
				(List<WorkflowMetricsSLADefinitionVersion>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion : list) {

					if (!uuid.equals(
							workflowMetricsSLADefinitionVersion.getUuid()) ||
						(companyId !=
							workflowMetricsSLADefinitionVersion.
								getCompanyId())) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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
				query.append(
					WorkflowMetricsSLADefinitionVersionModelImpl.ORDER_BY_JPQL);
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

				list =
					(List<WorkflowMetricsSLADefinitionVersion>)QueryUtil.list(
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
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = fetchByUuid_C_First(
				uuid, companyId, orderByComparator);

		if (workflowMetricsSLADefinitionVersion != null) {
			return workflowMetricsSLADefinitionVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLADefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		List<WorkflowMetricsSLADefinitionVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = fetchByUuid_C_Last(
				uuid, companyId, orderByComparator);

		if (workflowMetricsSLADefinitionVersion != null) {
			return workflowMetricsSLADefinitionVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSLADefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinitionVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definition versions before and after the current workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the current workflow metrics sla definition version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLADefinitionVersionId, String uuid,
			long companyId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		uuid = Objects.toString(uuid, "");

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = findByPrimaryKey(
				workflowMetricsSLADefinitionVersionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinitionVersion[] array =
				new WorkflowMetricsSLADefinitionVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLADefinitionVersion, uuid, companyId,
				orderByComparator, true);

			array[1] = workflowMetricsSLADefinitionVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, workflowMetricsSLADefinitionVersion, uuid, companyId,
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

	protected WorkflowMetricsSLADefinitionVersion getByUuid_C_PrevAndNext(
		Session session,
		WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion,
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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
			query.append(
				WorkflowMetricsSLADefinitionVersionModelImpl.ORDER_BY_JPQL);
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
						workflowMetricsSLADefinitionVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinitionVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion :
					findByUuid_C(
						uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(workflowMetricsSLADefinitionVersion);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

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
		"workflowMetricsSLADefinitionVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(workflowMetricsSLADefinitionVersion.uuid IS NULL OR workflowMetricsSLADefinitionVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"workflowMetricsSLADefinitionVersion.companyId = ?";

	private FinderPath
		_finderPathWithPaginationFindByWorkflowMetricsSLADefinitionId;
	private FinderPath
		_finderPathWithoutPaginationFindByWorkflowMetricsSLADefinitionId;
	private FinderPath _finderPathCountByWorkflowMetricsSLADefinitionId;

	/**
	 * Returns all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId) {

		return findByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @return the range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end) {

		return findByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		return findByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByWorkflowMetricsSLADefinitionId;
				finderArgs = new Object[] {workflowMetricsSLADefinitionId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByWorkflowMetricsSLADefinitionId;
			finderArgs = new Object[] {
				workflowMetricsSLADefinitionId, start, end, orderByComparator
			};
		}

		List<WorkflowMetricsSLADefinitionVersion> list = null;

		if (useFinderCache) {
			list =
				(List<WorkflowMetricsSLADefinitionVersion>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion : list) {

					if (workflowMetricsSLADefinitionId !=
							workflowMetricsSLADefinitionVersion.
								getWorkflowMetricsSLADefinitionId()) {

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

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_WORKFLOWMETRICSSLADEFINITIONID_WORKFLOWMETRICSSLADEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(
					WorkflowMetricsSLADefinitionVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(workflowMetricsSLADefinitionId);

				list =
					(List<WorkflowMetricsSLADefinitionVersion>)QueryUtil.list(
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
	 * Returns the first workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion
			findByWorkflowMetricsSLADefinitionId_First(
				long workflowMetricsSLADefinitionId,
				OrderByComparator<WorkflowMetricsSLADefinitionVersion>
					orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				fetchByWorkflowMetricsSLADefinitionId_First(
					workflowMetricsSLADefinitionId, orderByComparator);

		if (workflowMetricsSLADefinitionVersion != null) {
			return workflowMetricsSLADefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("workflowMetricsSLADefinitionId=");
		msg.append(workflowMetricsSLADefinitionId);

		msg.append("}");

		throw new NoSuchSLADefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion
		fetchByWorkflowMetricsSLADefinitionId_First(
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		List<WorkflowMetricsSLADefinitionVersion> list =
			findByWorkflowMetricsSLADefinitionId(
				workflowMetricsSLADefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion
			findByWorkflowMetricsSLADefinitionId_Last(
				long workflowMetricsSLADefinitionId,
				OrderByComparator<WorkflowMetricsSLADefinitionVersion>
					orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				fetchByWorkflowMetricsSLADefinitionId_Last(
					workflowMetricsSLADefinitionId, orderByComparator);

		if (workflowMetricsSLADefinitionVersion != null) {
			return workflowMetricsSLADefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("workflowMetricsSLADefinitionId=");
		msg.append(workflowMetricsSLADefinitionId);

		msg.append("}");

		throw new NoSuchSLADefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion
		fetchByWorkflowMetricsSLADefinitionId_Last(
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		int count = countByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId);

		if (count == 0) {
			return null;
		}

		List<WorkflowMetricsSLADefinitionVersion> list =
			findByWorkflowMetricsSLADefinitionId(
				workflowMetricsSLADefinitionId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workflow metrics sla definition versions before and after the current workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the current workflow metrics sla definition version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion[]
			findByWorkflowMetricsSLADefinitionId_PrevAndNext(
				long workflowMetricsSLADefinitionVersionId,
				long workflowMetricsSLADefinitionId,
				OrderByComparator<WorkflowMetricsSLADefinitionVersion>
					orderByComparator)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = findByPrimaryKey(
				workflowMetricsSLADefinitionVersionId);

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinitionVersion[] array =
				new WorkflowMetricsSLADefinitionVersionImpl[3];

			array[0] = getByWorkflowMetricsSLADefinitionId_PrevAndNext(
				session, workflowMetricsSLADefinitionVersion,
				workflowMetricsSLADefinitionId, orderByComparator, true);

			array[1] = workflowMetricsSLADefinitionVersion;

			array[2] = getByWorkflowMetricsSLADefinitionId_PrevAndNext(
				session, workflowMetricsSLADefinitionVersion,
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

	protected WorkflowMetricsSLADefinitionVersion
		getByWorkflowMetricsSLADefinitionId_PrevAndNext(
			Session session,
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion,
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator,
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

		query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

		query.append(
			_FINDER_COLUMN_WORKFLOWMETRICSSLADEFINITIONID_WORKFLOWMETRICSSLADEFINITIONID_2);

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
			query.append(
				WorkflowMetricsSLADefinitionVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(workflowMetricsSLADefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						workflowMetricsSLADefinitionVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<WorkflowMetricsSLADefinitionVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 */
	@Override
	public void removeByWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		for (WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion :
					findByWorkflowMetricsSLADefinitionId(
						workflowMetricsSLADefinitionId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(workflowMetricsSLADefinitionVersion);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	@Override
	public int countByWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		FinderPath finderPath =
			_finderPathCountByWorkflowMetricsSLADefinitionId;

		Object[] finderArgs = new Object[] {workflowMetricsSLADefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_WORKFLOWMETRICSSLADEFINITIONID_WORKFLOWMETRICSSLADEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String
		_FINDER_COLUMN_WORKFLOWMETRICSSLADEFINITIONID_WORKFLOWMETRICSSLADEFINITIONID_2 =
			"workflowMetricsSLADefinitionVersion.workflowMetricsSLADefinitionId = ?";

	private FinderPath _finderPathFetchByV_WMSLAD;
	private FinderPath _finderPathCountByV_WMSLAD;

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByV_WMSLAD(
			String version, long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = fetchByV_WMSLAD(
				version, workflowMetricsSLADefinitionId);

		if (workflowMetricsSLADefinitionVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("version=");
			msg.append(version);

			msg.append(", workflowMetricsSLADefinitionId=");
			msg.append(workflowMetricsSLADefinitionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSLADefinitionVersionException(msg.toString());
		}

		return workflowMetricsSLADefinitionVersion;
	}

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId) {

		return fetchByV_WMSLAD(version, workflowMetricsSLADefinitionId, true);
	}

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId,
		boolean useFinderCache) {

		version = Objects.toString(version, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {version, workflowMetricsSLADefinitionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByV_WMSLAD, finderArgs, this);
		}

		if (result instanceof WorkflowMetricsSLADefinitionVersion) {
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion =
					(WorkflowMetricsSLADefinitionVersion)result;

			if (!Objects.equals(
					version,
					workflowMetricsSLADefinitionVersion.getVersion()) ||
				(workflowMetricsSLADefinitionId !=
					workflowMetricsSLADefinitionVersion.
						getWorkflowMetricsSLADefinitionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_V_WMSLAD_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_V_WMSLAD_VERSION_2);
			}

			query.append(
				_FINDER_COLUMN_V_WMSLAD_WORKFLOWMETRICSSLADEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindVersion) {
					qPos.add(version);
				}

				qPos.add(workflowMetricsSLADefinitionId);

				List<WorkflowMetricsSLADefinitionVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByV_WMSLAD, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									version, workflowMetricsSLADefinitionId
								};
							}

							_log.warn(
								"WorkflowMetricsSLADefinitionVersionPersistenceImpl.fetchByV_WMSLAD(String, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion = list.get(0);

					result = workflowMetricsSLADefinitionVersion;

					cacheResult(workflowMetricsSLADefinitionVersion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByV_WMSLAD, finderArgs);
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
			return (WorkflowMetricsSLADefinitionVersion)result;
		}
	}

	/**
	 * Removes the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the workflow metrics sla definition version that was removed
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion removeByV_WMSLAD(
			String version, long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = findByV_WMSLAD(
				version, workflowMetricsSLADefinitionId);

		return remove(workflowMetricsSLADefinitionVersion);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where version = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	@Override
	public int countByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId) {

		version = Objects.toString(version, "");

		FinderPath finderPath = _finderPathCountByV_WMSLAD;

		Object[] finderArgs = new Object[] {
			version, workflowMetricsSLADefinitionId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_V_WMSLAD_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_V_WMSLAD_VERSION_2);
			}

			query.append(
				_FINDER_COLUMN_V_WMSLAD_WORKFLOWMETRICSSLADEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindVersion) {
					qPos.add(version);
				}

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

	private static final String _FINDER_COLUMN_V_WMSLAD_VERSION_2 =
		"workflowMetricsSLADefinitionVersion.version = ? AND ";

	private static final String _FINDER_COLUMN_V_WMSLAD_VERSION_3 =
		"(workflowMetricsSLADefinitionVersion.version IS NULL OR workflowMetricsSLADefinitionVersion.version = '') AND ";

	private static final String
		_FINDER_COLUMN_V_WMSLAD_WORKFLOWMETRICSSLADEFINITIONID_2 =
			"workflowMetricsSLADefinitionVersion.workflowMetricsSLADefinitionId = ?";

	public WorkflowMetricsSLADefinitionVersionPersistenceImpl() {
		setModelClass(WorkflowMetricsSLADefinitionVersion.class);

		setModelImplClass(WorkflowMetricsSLADefinitionVersionImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"workflowMetricsSLADefinitionVersionId",
			"wmSLADefinitionVersionId");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put(
			"workflowMetricsSLADefinitionId", "wmSLADefinitionId");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the workflow metrics sla definition version in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitionVersion the workflow metrics sla definition version
	 */
	@Override
	public void cacheResult(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		entityCache.putResult(
			entityCacheEnabled, WorkflowMetricsSLADefinitionVersionImpl.class,
			workflowMetricsSLADefinitionVersion.getPrimaryKey(),
			workflowMetricsSLADefinitionVersion);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				workflowMetricsSLADefinitionVersion.getUuid(),
				workflowMetricsSLADefinitionVersion.getGroupId()
			},
			workflowMetricsSLADefinitionVersion);

		finderCache.putResult(
			_finderPathFetchByV_WMSLAD,
			new Object[] {
				workflowMetricsSLADefinitionVersion.getVersion(),
				workflowMetricsSLADefinitionVersion.
					getWorkflowMetricsSLADefinitionId()
			},
			workflowMetricsSLADefinitionVersion);

		workflowMetricsSLADefinitionVersion.resetOriginalValues();
	}

	/**
	 * Caches the workflow metrics sla definition versions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitionVersions the workflow metrics sla definition versions
	 */
	@Override
	public void cacheResult(
		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions) {

		for (WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion :
					workflowMetricsSLADefinitionVersions) {

			if (entityCache.getResult(
					entityCacheEnabled,
					WorkflowMetricsSLADefinitionVersionImpl.class,
					workflowMetricsSLADefinitionVersion.getPrimaryKey()) ==
						null) {

				cacheResult(workflowMetricsSLADefinitionVersion);
			}
			else {
				workflowMetricsSLADefinitionVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all workflow metrics sla definition versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WorkflowMetricsSLADefinitionVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the workflow metrics sla definition version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		entityCache.removeResult(
			entityCacheEnabled, WorkflowMetricsSLADefinitionVersionImpl.class,
			workflowMetricsSLADefinitionVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(WorkflowMetricsSLADefinitionVersionModelImpl)
				workflowMetricsSLADefinitionVersion,
			true);
	}

	@Override
	public void clearCache(
		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion :
					workflowMetricsSLADefinitionVersions) {

			entityCache.removeResult(
				entityCacheEnabled,
				WorkflowMetricsSLADefinitionVersionImpl.class,
				workflowMetricsSLADefinitionVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(WorkflowMetricsSLADefinitionVersionModelImpl)
					workflowMetricsSLADefinitionVersion,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled,
				WorkflowMetricsSLADefinitionVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		WorkflowMetricsSLADefinitionVersionModelImpl
			workflowMetricsSLADefinitionVersionModelImpl) {

		Object[] args = new Object[] {
			workflowMetricsSLADefinitionVersionModelImpl.getUuid(),
			workflowMetricsSLADefinitionVersionModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			workflowMetricsSLADefinitionVersionModelImpl, false);

		args = new Object[] {
			workflowMetricsSLADefinitionVersionModelImpl.getVersion(),
			workflowMetricsSLADefinitionVersionModelImpl.
				getWorkflowMetricsSLADefinitionId()
		};

		finderCache.putResult(
			_finderPathCountByV_WMSLAD, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByV_WMSLAD, args,
			workflowMetricsSLADefinitionVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		WorkflowMetricsSLADefinitionVersionModelImpl
			workflowMetricsSLADefinitionVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				workflowMetricsSLADefinitionVersionModelImpl.getUuid(),
				workflowMetricsSLADefinitionVersionModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((workflowMetricsSLADefinitionVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				workflowMetricsSLADefinitionVersionModelImpl.getOriginalUuid(),
				workflowMetricsSLADefinitionVersionModelImpl.
					getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				workflowMetricsSLADefinitionVersionModelImpl.getVersion(),
				workflowMetricsSLADefinitionVersionModelImpl.
					getWorkflowMetricsSLADefinitionId()
			};

			finderCache.removeResult(_finderPathCountByV_WMSLAD, args);
			finderCache.removeResult(_finderPathFetchByV_WMSLAD, args);
		}

		if ((workflowMetricsSLADefinitionVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByV_WMSLAD.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				workflowMetricsSLADefinitionVersionModelImpl.
					getOriginalVersion(),
				workflowMetricsSLADefinitionVersionModelImpl.
					getOriginalWorkflowMetricsSLADefinitionId()
			};

			finderCache.removeResult(_finderPathCountByV_WMSLAD, args);
			finderCache.removeResult(_finderPathFetchByV_WMSLAD, args);
		}
	}

	/**
	 * Creates a new workflow metrics sla definition version with the primary key. Does not add the workflow metrics sla definition version to the database.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key for the new workflow metrics sla definition version
	 * @return the new workflow metrics sla definition version
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion create(
		long workflowMetricsSLADefinitionVersionId) {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				new WorkflowMetricsSLADefinitionVersionImpl();

		workflowMetricsSLADefinitionVersion.setNew(true);
		workflowMetricsSLADefinitionVersion.setPrimaryKey(
			workflowMetricsSLADefinitionVersionId);

		String uuid = PortalUUIDUtil.generate();

		workflowMetricsSLADefinitionVersion.setUuid(uuid);

		workflowMetricsSLADefinitionVersion.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return workflowMetricsSLADefinitionVersion;
	}

	/**
	 * Removes the workflow metrics sla definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was removed
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion remove(
			long workflowMetricsSLADefinitionVersionId)
		throws NoSuchSLADefinitionVersionException {

		return remove((Serializable)workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Removes the workflow metrics sla definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was removed
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion remove(Serializable primaryKey)
		throws NoSuchSLADefinitionVersionException {

		Session session = null;

		try {
			session = openSession();

			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion =
					(WorkflowMetricsSLADefinitionVersion)session.get(
						WorkflowMetricsSLADefinitionVersionImpl.class,
						primaryKey);

			if (workflowMetricsSLADefinitionVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSLADefinitionVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(workflowMetricsSLADefinitionVersion);
		}
		catch (NoSuchSLADefinitionVersionException nsee) {
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
	protected WorkflowMetricsSLADefinitionVersion removeImpl(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(workflowMetricsSLADefinitionVersion)) {
				workflowMetricsSLADefinitionVersion =
					(WorkflowMetricsSLADefinitionVersion)session.get(
						WorkflowMetricsSLADefinitionVersionImpl.class,
						workflowMetricsSLADefinitionVersion.getPrimaryKeyObj());
			}

			if (workflowMetricsSLADefinitionVersion != null) {
				session.delete(workflowMetricsSLADefinitionVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (workflowMetricsSLADefinitionVersion != null) {
			clearCache(workflowMetricsSLADefinitionVersion);
		}

		return workflowMetricsSLADefinitionVersion;
	}

	@Override
	public WorkflowMetricsSLADefinitionVersion updateImpl(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		boolean isNew = workflowMetricsSLADefinitionVersion.isNew();

		if (!(workflowMetricsSLADefinitionVersion instanceof
				WorkflowMetricsSLADefinitionVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					workflowMetricsSLADefinitionVersion.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					workflowMetricsSLADefinitionVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in workflowMetricsSLADefinitionVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WorkflowMetricsSLADefinitionVersion implementation " +
					workflowMetricsSLADefinitionVersion.getClass());
		}

		WorkflowMetricsSLADefinitionVersionModelImpl
			workflowMetricsSLADefinitionVersionModelImpl =
				(WorkflowMetricsSLADefinitionVersionModelImpl)
					workflowMetricsSLADefinitionVersion;

		if (Validator.isNull(workflowMetricsSLADefinitionVersion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			workflowMetricsSLADefinitionVersion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
			(workflowMetricsSLADefinitionVersion.getCreateDate() == null)) {

			if (serviceContext == null) {
				workflowMetricsSLADefinitionVersion.setCreateDate(now);
			}
			else {
				workflowMetricsSLADefinitionVersion.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!workflowMetricsSLADefinitionVersionModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				workflowMetricsSLADefinitionVersion.setModifiedDate(now);
			}
			else {
				workflowMetricsSLADefinitionVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (workflowMetricsSLADefinitionVersion.isNew()) {
				session.save(workflowMetricsSLADefinitionVersion);

				workflowMetricsSLADefinitionVersion.setNew(false);
			}
			else {
				workflowMetricsSLADefinitionVersion =
					(WorkflowMetricsSLADefinitionVersion)session.merge(
						workflowMetricsSLADefinitionVersion);
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
				workflowMetricsSLADefinitionVersionModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				workflowMetricsSLADefinitionVersionModelImpl.getUuid(),
				workflowMetricsSLADefinitionVersionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				workflowMetricsSLADefinitionVersionModelImpl.
					getWorkflowMetricsSLADefinitionId()
			};

			finderCache.removeResult(
				_finderPathCountByWorkflowMetricsSLADefinitionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByWorkflowMetricsSLADefinitionId,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((workflowMetricsSLADefinitionVersionModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLADefinitionVersionModelImpl.
						getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					workflowMetricsSLADefinitionVersionModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((workflowMetricsSLADefinitionVersionModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					workflowMetricsSLADefinitionVersionModelImpl.
						getOriginalUuid(),
					workflowMetricsSLADefinitionVersionModelImpl.
						getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					workflowMetricsSLADefinitionVersionModelImpl.getUuid(),
					workflowMetricsSLADefinitionVersionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((workflowMetricsSLADefinitionVersionModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByWorkflowMetricsSLADefinitionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					workflowMetricsSLADefinitionVersionModelImpl.
						getOriginalWorkflowMetricsSLADefinitionId()
				};

				finderCache.removeResult(
					_finderPathCountByWorkflowMetricsSLADefinitionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByWorkflowMetricsSLADefinitionId,
					args);

				args = new Object[] {
					workflowMetricsSLADefinitionVersionModelImpl.
						getWorkflowMetricsSLADefinitionId()
				};

				finderCache.removeResult(
					_finderPathCountByWorkflowMetricsSLADefinitionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByWorkflowMetricsSLADefinitionId,
					args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, WorkflowMetricsSLADefinitionVersionImpl.class,
			workflowMetricsSLADefinitionVersion.getPrimaryKey(),
			workflowMetricsSLADefinitionVersion, false);

		clearUniqueFindersCache(
			workflowMetricsSLADefinitionVersionModelImpl, false);
		cacheUniqueFindersCache(workflowMetricsSLADefinitionVersionModelImpl);

		workflowMetricsSLADefinitionVersion.resetOriginalValues();

		return workflowMetricsSLADefinitionVersion;
	}

	/**
	 * Returns the workflow metrics sla definition version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchSLADefinitionVersionException {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = fetchByPrimaryKey(primaryKey);

		if (workflowMetricsSLADefinitionVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSLADefinitionVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return workflowMetricsSLADefinitionVersion;
	}

	/**
	 * Returns the workflow metrics sla definition version with the primary key or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion findByPrimaryKey(
			long workflowMetricsSLADefinitionVersionId)
		throws NoSuchSLADefinitionVersionException {

		return findByPrimaryKey(
			(Serializable)workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Returns the workflow metrics sla definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version, or <code>null</code> if a workflow metrics sla definition version with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLADefinitionVersion fetchByPrimaryKey(
		long workflowMetricsSLADefinitionVersionId) {

		return fetchByPrimaryKey(
			(Serializable)workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Returns all the workflow metrics sla definition versions.
	 *
	 * @return the workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workflow metrics sla definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @return the range of workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the workflow metrics sla definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of workflow metrics sla definition versions
	 */
	@Override
	public List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
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

		List<WorkflowMetricsSLADefinitionVersion> list = null;

		if (useFinderCache) {
			list =
				(List<WorkflowMetricsSLADefinitionVersion>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION;

				sql = sql.concat(
					WorkflowMetricsSLADefinitionVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list =
					(List<WorkflowMetricsSLADefinitionVersion>)QueryUtil.list(
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
	 * Removes all the workflow metrics sla definition versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion : findAll()) {

			remove(workflowMetricsSLADefinitionVersion);
		}
	}

	/**
	 * Returns the number of workflow metrics sla definition versions.
	 *
	 * @return the number of workflow metrics sla definition versions
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
					_SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION);

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
		return "wmSLADefinitionVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WorkflowMetricsSLADefinitionVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the workflow metrics sla definition version persistence.
	 */
	@Activate
	public void activate() {
		WorkflowMetricsSLADefinitionVersionModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		WorkflowMetricsSLADefinitionVersionModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			WorkflowMetricsSLADefinitionVersionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionVersionModelImpl.
				MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLADefinitionVersionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionVersionModelImpl.
				GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLADefinitionVersionModelImpl.UUID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionVersionModelImpl.
				COMPANYID_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionVersionModelImpl.
				MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByWorkflowMetricsSLADefinitionId =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				WorkflowMetricsSLADefinitionVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByWorkflowMetricsSLADefinitionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByWorkflowMetricsSLADefinitionId =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				WorkflowMetricsSLADefinitionVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByWorkflowMetricsSLADefinitionId",
				new String[] {Long.class.getName()},
				WorkflowMetricsSLADefinitionVersionModelImpl.
					WORKFLOWMETRICSSLADEFINITIONID_COLUMN_BITMASK |
				WorkflowMetricsSLADefinitionVersionModelImpl.
					MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByWorkflowMetricsSLADefinitionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByWorkflowMetricsSLADefinitionId",
			new String[] {Long.class.getName()});

		_finderPathFetchByV_WMSLAD = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			WorkflowMetricsSLADefinitionVersionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByV_WMSLAD",
			new String[] {String.class.getName(), Long.class.getName()},
			WorkflowMetricsSLADefinitionVersionModelImpl.
				VERSION_COLUMN_BITMASK |
			WorkflowMetricsSLADefinitionVersionModelImpl.
				WORKFLOWMETRICSSLADEFINITIONID_COLUMN_BITMASK);

		_finderPathCountByV_WMSLAD = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByV_WMSLAD",
			new String[] {String.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			WorkflowMetricsSLADefinitionVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = WorkflowMetricsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String
		_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION =
			"SELECT workflowMetricsSLADefinitionVersion FROM WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion";

	private static final String
		_SQL_SELECT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE =
			"SELECT workflowMetricsSLADefinitionVersion FROM WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion WHERE ";

	private static final String _SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION =
		"SELECT COUNT(workflowMetricsSLADefinitionVersion) FROM WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion";

	private static final String
		_SQL_COUNT_WORKFLOWMETRICSSLADEFINITIONVERSION_WHERE =
			"SELECT COUNT(workflowMetricsSLADefinitionVersion) FROM WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"workflowMetricsSLADefinitionVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WorkflowMetricsSLADefinitionVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WorkflowMetricsSLADefinitionVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsSLADefinitionVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"uuid", "workflowMetricsSLADefinitionVersionId", "active",
			"workflowMetricsSLADefinitionId"
		});

	static {
		try {
			Class.forName(WorkflowMetricsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}