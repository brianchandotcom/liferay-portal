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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the workflow metrics sla definition service. This utility wraps <code>com.liferay.portal.workflow.metrics.service.persistence.impl.WorkflowMetricsSLADefinitionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionPersistence
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLADefinitionUtil {

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
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		getPersistence().clearCache(workflowMetricsSLADefinition);
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
	public static Map<Serializable, WorkflowMetricsSLADefinition>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WorkflowMetricsSLADefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WorkflowMetricsSLADefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WorkflowMetricsSLADefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WorkflowMetricsSLADefinition update(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return getPersistence().update(workflowMetricsSLADefinition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WorkflowMetricsSLADefinition update(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		ServiceContext serviceContext) {

		return getPersistence().update(
			workflowMetricsSLADefinition, serviceContext);
	}

	/**
	 * Returns all the workflow metrics sla definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla definitions
	 */
	public static List<WorkflowMetricsSLADefinition> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
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
	public static List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
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
	public static List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
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
	public static WorkflowMetricsSLADefinition[] findByUuid_PrevAndNext(
			long workflowMetricsSLADefinitionId, String uuid,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByUuid_PrevAndNext(
			workflowMetricsSLADefinitionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla definitions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the workflow metrics sla definition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla definition that was removed
	 */
	public static WorkflowMetricsSLADefinition removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla definitions
	 */
	public static List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
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
	public static List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
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
	public static List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
	public static WorkflowMetricsSLADefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static WorkflowMetricsSLADefinition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
	public static WorkflowMetricsSLADefinition[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLADefinitionId, String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			workflowMetricsSLADefinitionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definitions
	 */
	public static List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId) {

		return getPersistence().findByC_P(companyId, processId);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end) {

		return getPersistence().findByC_P(companyId, processId, start, end);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().findByC_P(
			companyId, processId, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_P(
			companyId, processId, start, end, orderByComparator,
			useFinderCache);
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
	public static WorkflowMetricsSLADefinition findByC_P_First(
			long companyId, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_First(
			companyId, processId, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByC_P_First(
		long companyId, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByC_P_First(
			companyId, processId, orderByComparator);
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
	public static WorkflowMetricsSLADefinition findByC_P_Last(
			long companyId, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_Last(
			companyId, processId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByC_P_Last(
		long companyId, long processId,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByC_P_Last(
			companyId, processId, orderByComparator);
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
	public static WorkflowMetricsSLADefinition[] findByC_P_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_PrevAndNext(
			workflowMetricsSLADefinitionId, companyId, processId,
			orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 */
	public static void removeByC_P(long companyId, long processId) {
		getPersistence().removeByC_P(companyId, processId);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public static int countByC_P(long companyId, long processId) {
		return getPersistence().countByC_P(companyId, processId);
	}

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition findByC_N_P(
			long companyId, String name, long processId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_N_P(companyId, name, processId);
	}

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByC_N_P(
		long companyId, String name, long processId) {

		return getPersistence().fetchByC_N_P(companyId, name, processId);
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
	public static WorkflowMetricsSLADefinition fetchByC_N_P(
		long companyId, String name, long processId, boolean useFinderCache) {

		return getPersistence().fetchByC_N_P(
			companyId, name, processId, useFinderCache);
	}

	/**
	 * Removes the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the workflow metrics sla definition that was removed
	 */
	public static WorkflowMetricsSLADefinition removeByC_N_P(
			long companyId, String name, long processId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().removeByC_N_P(companyId, name, processId);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public static int countByC_N_P(
		long companyId, String name, long processId) {

		return getPersistence().countByC_N_P(companyId, name, processId);
	}

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	public static List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status) {

		return getPersistence().findByC_P_S(companyId, processId, status);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end) {

		return getPersistence().findByC_P_S(
			companyId, processId, status, start, end);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().findByC_P_S(
			companyId, processId, status, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_P_S(
			companyId, processId, status, start, end, orderByComparator,
			useFinderCache);
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
	public static WorkflowMetricsSLADefinition findByC_P_S_First(
			long companyId, long processId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_S_First(
			companyId, processId, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition fetchByC_P_S_First(
		long companyId, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByC_P_S_First(
			companyId, processId, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition findByC_P_S_Last(
			long companyId, long processId, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_S_Last(
			companyId, processId, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition fetchByC_P_S_Last(
		long companyId, long processId, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByC_P_S_Last(
			companyId, processId, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition[] findByC_P_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_S_PrevAndNext(
			workflowMetricsSLADefinitionId, companyId, processId, status,
			orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 */
	public static void removeByC_P_S(
		long companyId, long processId, int status) {

		getPersistence().removeByC_P_S(companyId, processId, status);
	}

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	public static int countByC_P_S(long companyId, long processId, int status) {
		return getPersistence().countByC_P_S(companyId, processId, status);
	}

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	public static List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status) {

		return getPersistence().findByC_P_NotPV_S(
			companyId, processId, processVersion, status);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end) {

		return getPersistence().findByC_P_NotPV_S(
			companyId, processId, processVersion, status, start, end);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().findByC_P_NotPV_S(
			companyId, processId, processVersion, status, start, end,
			orderByComparator);
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
	public static List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_P_NotPV_S(
			companyId, processId, processVersion, status, start, end,
			orderByComparator, useFinderCache);
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
	public static WorkflowMetricsSLADefinition findByC_P_NotPV_S_First(
			long companyId, long processId, String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_NotPV_S_First(
			companyId, processId, processVersion, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition fetchByC_P_NotPV_S_First(
		long companyId, long processId, String processVersion, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByC_P_NotPV_S_First(
			companyId, processId, processVersion, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition findByC_P_NotPV_S_Last(
			long companyId, long processId, String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_NotPV_S_Last(
			companyId, processId, processVersion, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition fetchByC_P_NotPV_S_Last(
		long companyId, long processId, String processVersion, int status,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().fetchByC_P_NotPV_S_Last(
			companyId, processId, processVersion, status, orderByComparator);
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
	public static WorkflowMetricsSLADefinition[] findByC_P_NotPV_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			String processVersion, int status,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByC_P_NotPV_S_PrevAndNext(
			workflowMetricsSLADefinitionId, companyId, processId,
			processVersion, status, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 */
	public static void removeByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status) {

		getPersistence().removeByC_P_NotPV_S(
			companyId, processId, processVersion, status);
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
	public static int countByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status) {

		return getPersistence().countByC_P_NotPV_S(
			companyId, processId, processVersion, status);
	}

	/**
	 * Caches the workflow metrics sla definition in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 */
	public static void cacheResult(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		getPersistence().cacheResult(workflowMetricsSLADefinition);
	}

	/**
	 * Caches the workflow metrics sla definitions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitions the workflow metrics sla definitions
	 */
	public static void cacheResult(
		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions) {

		getPersistence().cacheResult(workflowMetricsSLADefinitions);
	}

	/**
	 * Creates a new workflow metrics sla definition with the primary key. Does not add the workflow metrics sla definition to the database.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key for the new workflow metrics sla definition
	 * @return the new workflow metrics sla definition
	 */
	public static WorkflowMetricsSLADefinition create(
		long workflowMetricsSLADefinitionId) {

		return getPersistence().create(workflowMetricsSLADefinitionId);
	}

	/**
	 * Removes the workflow metrics sla definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	public static WorkflowMetricsSLADefinition remove(
			long workflowMetricsSLADefinitionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().remove(workflowMetricsSLADefinitionId);
	}

	public static WorkflowMetricsSLADefinition updateImpl(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return getPersistence().updateImpl(workflowMetricsSLADefinition);
	}

	/**
	 * Returns the workflow metrics sla definition with the primary key or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	public static WorkflowMetricsSLADefinition findByPrimaryKey(
			long workflowMetricsSLADefinitionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionException {

		return getPersistence().findByPrimaryKey(
			workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the workflow metrics sla definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition, or <code>null</code> if a workflow metrics sla definition with the primary key could not be found
	 */
	public static WorkflowMetricsSLADefinition fetchByPrimaryKey(
		long workflowMetricsSLADefinitionId) {

		return getPersistence().fetchByPrimaryKey(
			workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns all the workflow metrics sla definitions.
	 *
	 * @return the workflow metrics sla definitions
	 */
	public static List<WorkflowMetricsSLADefinition> findAll() {
		return getPersistence().findAll();
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
	public static List<WorkflowMetricsSLADefinition> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
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
	public static List<WorkflowMetricsSLADefinition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinition> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the workflow metrics sla definitions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of workflow metrics sla definitions.
	 *
	 * @return the number of workflow metrics sla definitions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WorkflowMetricsSLADefinitionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WorkflowMetricsSLADefinitionPersistence,
		 WorkflowMetricsSLADefinitionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowMetricsSLADefinitionPersistence.class);

		ServiceTracker
			<WorkflowMetricsSLADefinitionPersistence,
			 WorkflowMetricsSLADefinitionPersistence> serviceTracker =
				new ServiceTracker
					<WorkflowMetricsSLADefinitionPersistence,
					 WorkflowMetricsSLADefinitionPersistence>(
						 bundle.getBundleContext(),
						 WorkflowMetricsSLADefinitionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}