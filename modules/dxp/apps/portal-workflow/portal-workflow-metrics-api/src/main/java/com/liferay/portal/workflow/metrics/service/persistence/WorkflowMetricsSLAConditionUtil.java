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

package com.liferay.portal.workflow.metrics.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the workflow metrics sla condition service. This utility wraps <code>com.liferay.portal.workflow.metrics.service.persistence.impl.WorkflowMetricsSLAConditionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLAConditionPersistence
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLAConditionUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		getPersistence().clearCache(workflowMetricsSLACondition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, WorkflowMetricsSLACondition>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WorkflowMetricsSLACondition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WorkflowMetricsSLACondition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WorkflowMetricsSLACondition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WorkflowMetricsSLACondition update(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return getPersistence().update(workflowMetricsSLACondition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WorkflowMetricsSLACondition update(
		WorkflowMetricsSLACondition workflowMetricsSLACondition,
		ServiceContext serviceContext) {

		return getPersistence().update(
			workflowMetricsSLACondition, serviceContext);
	}

	/**
	 * Returns all the workflow metrics sla conditions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla conditions
	 */
	public static List<WorkflowMetricsSLACondition> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
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
	public static List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
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
	public static List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
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
	public static WorkflowMetricsSLACondition[] findByUuid_PrevAndNext(
			long workflowMetricsSLAConditionId, String uuid,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByUuid_PrevAndNext(
			workflowMetricsSLAConditionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla conditions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla conditions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLAConditionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	 * Removes the workflow metrics sla condition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla condition that was removed
	 */
	public static WorkflowMetricsSLACondition removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla conditions
	 */
	public static List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
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
	public static List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
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
	public static List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
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
	public static WorkflowMetricsSLACondition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static WorkflowMetricsSLACondition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
	public static WorkflowMetricsSLACondition[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLAConditionId, String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			workflowMetricsSLAConditionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla conditions
	 */
	public static List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId) {

		return getPersistence().findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId);
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
	public static List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start,
		int end) {

		return getPersistence().findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, start, end);
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
	public static List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, start, end,
			orderByComparator);
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
	public static List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId, start, end,
			orderByComparator, retrieveFromCache);
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
	public static WorkflowMetricsSLACondition findByC_WMSLADI_First(
			long companyId, long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByC_WMSLADI_First(
			companyId, workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByC_WMSLADI_First(
		long companyId, long workflowMetricsSLADefinitionId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().fetchByC_WMSLADI_First(
			companyId, workflowMetricsSLADefinitionId, orderByComparator);
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
	public static WorkflowMetricsSLACondition findByC_WMSLADI_Last(
			long companyId, long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByC_WMSLADI_Last(
			companyId, workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByC_WMSLADI_Last(
		long companyId, long workflowMetricsSLADefinitionId,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().fetchByC_WMSLADI_Last(
			companyId, workflowMetricsSLADefinitionId, orderByComparator);
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
	public static WorkflowMetricsSLACondition[] findByC_WMSLADI_PrevAndNext(
			long workflowMetricsSLAConditionId, long companyId,
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByC_WMSLADI_PrevAndNext(
			workflowMetricsSLAConditionId, companyId,
			workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 */
	public static void removeByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId) {

		getPersistence().removeByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the number of workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	public static int countByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId) {

		return getPersistence().countByC_WMSLADI(
			companyId, workflowMetricsSLADefinitionId);
	}

	/**
	 * Caches the workflow metrics sla condition in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 */
	public static void cacheResult(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		getPersistence().cacheResult(workflowMetricsSLACondition);
	}

	/**
	 * Caches the workflow metrics sla conditions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLAConditions the workflow metrics sla conditions
	 */
	public static void cacheResult(
		List<WorkflowMetricsSLACondition> workflowMetricsSLAConditions) {

		getPersistence().cacheResult(workflowMetricsSLAConditions);
	}

	/**
	 * Creates a new workflow metrics sla condition with the primary key. Does not add the workflow metrics sla condition to the database.
	 *
	 * @param workflowMetricsSLAConditionId the primary key for the new workflow metrics sla condition
	 * @return the new workflow metrics sla condition
	 */
	public static WorkflowMetricsSLACondition create(
		long workflowMetricsSLAConditionId) {

		return getPersistence().create(workflowMetricsSLAConditionId);
	}

	/**
	 * Removes the workflow metrics sla condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	public static WorkflowMetricsSLACondition remove(
			long workflowMetricsSLAConditionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().remove(workflowMetricsSLAConditionId);
	}

	public static WorkflowMetricsSLACondition updateImpl(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return getPersistence().updateImpl(workflowMetricsSLACondition);
	}

	/**
	 * Returns the workflow metrics sla condition with the primary key or throws a <code>NoSuchSLAConditionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	public static WorkflowMetricsSLACondition findByPrimaryKey(
			long workflowMetricsSLAConditionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLAConditionException {

		return getPersistence().findByPrimaryKey(workflowMetricsSLAConditionId);
	}

	/**
	 * Returns the workflow metrics sla condition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition, or <code>null</code> if a workflow metrics sla condition with the primary key could not be found
	 */
	public static WorkflowMetricsSLACondition fetchByPrimaryKey(
		long workflowMetricsSLAConditionId) {

		return getPersistence().fetchByPrimaryKey(
			workflowMetricsSLAConditionId);
	}

	/**
	 * Returns all the workflow metrics sla conditions.
	 *
	 * @return the workflow metrics sla conditions
	 */
	public static List<WorkflowMetricsSLACondition> findAll() {
		return getPersistence().findAll();
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
	public static List<WorkflowMetricsSLACondition> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
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
	public static List<WorkflowMetricsSLACondition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLACondition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the workflow metrics sla conditions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of workflow metrics sla conditions.
	 *
	 * @return the number of workflow metrics sla conditions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WorkflowMetricsSLAConditionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WorkflowMetricsSLAConditionPersistence,
		 WorkflowMetricsSLAConditionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowMetricsSLAConditionPersistence.class);

		ServiceTracker
			<WorkflowMetricsSLAConditionPersistence,
			 WorkflowMetricsSLAConditionPersistence> serviceTracker =
				new ServiceTracker
					<WorkflowMetricsSLAConditionPersistence,
					 WorkflowMetricsSLAConditionPersistence>(
						 bundle.getBundleContext(),
						 WorkflowMetricsSLAConditionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}