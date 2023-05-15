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
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the workflow metrics sla definition version service. This utility wraps <code>com.liferay.portal.workflow.metrics.service.persistence.impl.WorkflowMetricsSLADefinitionVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionVersionPersistence
 * @generated
 */
public class WorkflowMetricsSLADefinitionVersionUtil {

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
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		getPersistence().clearCache(workflowMetricsSLADefinitionVersion);
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
	public static Map<Serializable, WorkflowMetricsSLADefinitionVersion>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WorkflowMetricsSLADefinitionVersion>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WorkflowMetricsSLADefinitionVersion>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WorkflowMetricsSLADefinitionVersion>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WorkflowMetricsSLADefinitionVersion update(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		return getPersistence().update(workflowMetricsSLADefinitionVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WorkflowMetricsSLADefinitionVersion update(
		WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(
			workflowMetricsSLADefinitionVersion, serviceContext);
	}

	/**
	 * Returns all the workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla definition versions
	 */
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid) {

		return getPersistence().findByUuid(uuid);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
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
	public static WorkflowMetricsSLADefinitionVersion[] findByUuid_PrevAndNext(
			long workflowMetricsSLADefinitionVersionId, String uuid,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByUuid_PrevAndNext(
			workflowMetricsSLADefinitionVersionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definition versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla definition version that was removed
	 */
	public static WorkflowMetricsSLADefinitionVersion removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla definition versions
	 */
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
	public static WorkflowMetricsSLADefinitionVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static WorkflowMetricsSLADefinitionVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
	public static WorkflowMetricsSLADefinitionVersion[]
			findByUuid_C_PrevAndNext(
				long workflowMetricsSLADefinitionVersionId, String uuid,
				long companyId,
				OrderByComparator<WorkflowMetricsSLADefinitionVersion>
					orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			workflowMetricsSLADefinitionVersionId, uuid, companyId,
			orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition versions
	 */
	public static List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId) {

		return getPersistence().findByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId);
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
	public static List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end) {

		return getPersistence().findByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId, start, end);
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
	public static List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		return getPersistence().findByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId, start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion
			findByWorkflowMetricsSLADefinitionId_First(
				long workflowMetricsSLADefinitionId,
				OrderByComparator<WorkflowMetricsSLADefinitionVersion>
					orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByWorkflowMetricsSLADefinitionId_First(
			workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion
		fetchByWorkflowMetricsSLADefinitionId_First(
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		return getPersistence().fetchByWorkflowMetricsSLADefinitionId_First(
			workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion
			findByWorkflowMetricsSLADefinitionId_Last(
				long workflowMetricsSLADefinitionId,
				OrderByComparator<WorkflowMetricsSLADefinitionVersion>
					orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByWorkflowMetricsSLADefinitionId_Last(
			workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion
		fetchByWorkflowMetricsSLADefinitionId_Last(
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		return getPersistence().fetchByWorkflowMetricsSLADefinitionId_Last(
			workflowMetricsSLADefinitionId, orderByComparator);
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
	public static WorkflowMetricsSLADefinitionVersion[]
			findByWorkflowMetricsSLADefinitionId_PrevAndNext(
				long workflowMetricsSLADefinitionVersionId,
				long workflowMetricsSLADefinitionId,
				OrderByComparator<WorkflowMetricsSLADefinitionVersion>
					orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().
			findByWorkflowMetricsSLADefinitionId_PrevAndNext(
				workflowMetricsSLADefinitionVersionId,
				workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 */
	public static void removeByWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		getPersistence().removeByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public static int countByWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		return getPersistence().countByWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion findByV_WMSLAD(
			String version, long workflowMetricsSLADefinitionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByV_WMSLAD(
			version, workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId) {

		return getPersistence().fetchByV_WMSLAD(
			version, workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId,
		boolean useFinderCache) {

		return getPersistence().fetchByV_WMSLAD(
			version, workflowMetricsSLADefinitionId, useFinderCache);
	}

	/**
	 * Removes the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the workflow metrics sla definition version that was removed
	 */
	public static WorkflowMetricsSLADefinitionVersion removeByV_WMSLAD(
			String version, long workflowMetricsSLADefinitionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().removeByV_WMSLAD(
			version, workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions where version = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public static int countByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId) {

		return getPersistence().countByV_WMSLAD(
			version, workflowMetricsSLADefinitionId);
	}

	/**
	 * Caches the workflow metrics sla definition version in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitionVersion the workflow metrics sla definition version
	 */
	public static void cacheResult(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		getPersistence().cacheResult(workflowMetricsSLADefinitionVersion);
	}

	/**
	 * Caches the workflow metrics sla definition versions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitionVersions the workflow metrics sla definition versions
	 */
	public static void cacheResult(
		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions) {

		getPersistence().cacheResult(workflowMetricsSLADefinitionVersions);
	}

	/**
	 * Creates a new workflow metrics sla definition version with the primary key. Does not add the workflow metrics sla definition version to the database.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key for the new workflow metrics sla definition version
	 * @return the new workflow metrics sla definition version
	 */
	public static WorkflowMetricsSLADefinitionVersion create(
		long workflowMetricsSLADefinitionVersionId) {

		return getPersistence().create(workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Removes the workflow metrics sla definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was removed
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion remove(
			long workflowMetricsSLADefinitionVersionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().remove(workflowMetricsSLADefinitionVersionId);
	}

	public static WorkflowMetricsSLADefinitionVersion updateImpl(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		return getPersistence().updateImpl(workflowMetricsSLADefinitionVersion);
	}

	/**
	 * Returns the workflow metrics sla definition version with the primary key or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion findByPrimaryKey(
			long workflowMetricsSLADefinitionVersionId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLADefinitionVersionException {

		return getPersistence().findByPrimaryKey(
			workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Returns the workflow metrics sla definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version, or <code>null</code> if a workflow metrics sla definition version with the primary key could not be found
	 */
	public static WorkflowMetricsSLADefinitionVersion fetchByPrimaryKey(
		long workflowMetricsSLADefinitionVersionId) {

		return getPersistence().fetchByPrimaryKey(
			workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Returns all the workflow metrics sla definition versions.
	 *
	 * @return the workflow metrics sla definition versions
	 */
	public static List<WorkflowMetricsSLADefinitionVersion> findAll() {
		return getPersistence().findAll();
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
	public static List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinitionVersion>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the workflow metrics sla definition versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of workflow metrics sla definition versions.
	 *
	 * @return the number of workflow metrics sla definition versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WorkflowMetricsSLADefinitionVersionPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		WorkflowMetricsSLADefinitionVersionPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile WorkflowMetricsSLADefinitionVersionPersistence
		_persistence;

}