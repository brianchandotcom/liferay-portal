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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLAConditionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition;

/**
 * The persistence interface for the workflow metrics sla condition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLAConditionUtil
 * @generated
 */
@ProviderType
public interface WorkflowMetricsSLAConditionPersistence
	extends BasePersistence<WorkflowMetricsSLACondition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WorkflowMetricsSLAConditionUtil} to access the workflow metrics sla condition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the workflow metrics sla conditions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla conditions
	 */
	public java.util.List<WorkflowMetricsSLACondition> findByUuid(String uuid);

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
	public java.util.List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLACondition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

	/**
	 * Returns the workflow metrics sla conditions before and after the current workflow metrics sla condition in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the current workflow metrics sla condition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	public WorkflowMetricsSLACondition[] findByUuid_PrevAndNext(
			long workflowMetricsSLAConditionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Removes all the workflow metrics sla conditions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla conditions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLAConditionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition findByUUID_G(String uuid, long groupId)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the workflow metrics sla condition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the workflow metrics sla condition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla condition that was removed
	 */
	public WorkflowMetricsSLACondition removeByUUID_G(String uuid, long groupId)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla conditions
	 */
	public java.util.List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLACondition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

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
	public WorkflowMetricsSLACondition[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLAConditionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Removes all the workflow metrics sla conditions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of workflow metrics sla conditions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla conditions
	 */
	public java.util.List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId);

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
	public java.util.List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start,
		int end);

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
	public java.util.List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLACondition> findByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition findByC_WMSLADI_First(
			long companyId, long workflowMetricsSLADefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the first workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByC_WMSLADI_First(
		long companyId, long workflowMetricsSLADefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition findByC_WMSLADI_Last(
			long companyId, long workflowMetricsSLADefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the last workflow metrics sla condition in the ordered set where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	public WorkflowMetricsSLACondition fetchByC_WMSLADI_Last(
		long companyId, long workflowMetricsSLADefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

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
	public WorkflowMetricsSLACondition[] findByC_WMSLADI_PrevAndNext(
			long workflowMetricsSLAConditionId, long companyId,
			long workflowMetricsSLADefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACondition> orderByComparator)
		throws NoSuchSLAConditionException;

	/**
	 * Removes all the workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 */
	public void removeByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId);

	/**
	 * Returns the number of workflow metrics sla conditions where companyId = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla conditions
	 */
	public int countByC_WMSLADI(
		long companyId, long workflowMetricsSLADefinitionId);

	/**
	 * Caches the workflow metrics sla condition in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 */
	public void cacheResult(
		WorkflowMetricsSLACondition workflowMetricsSLACondition);

	/**
	 * Caches the workflow metrics sla conditions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLAConditions the workflow metrics sla conditions
	 */
	public void cacheResult(
		java.util.List<WorkflowMetricsSLACondition>
			workflowMetricsSLAConditions);

	/**
	 * Creates a new workflow metrics sla condition with the primary key. Does not add the workflow metrics sla condition to the database.
	 *
	 * @param workflowMetricsSLAConditionId the primary key for the new workflow metrics sla condition
	 * @return the new workflow metrics sla condition
	 */
	public WorkflowMetricsSLACondition create(
		long workflowMetricsSLAConditionId);

	/**
	 * Removes the workflow metrics sla condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	public WorkflowMetricsSLACondition remove(
			long workflowMetricsSLAConditionId)
		throws NoSuchSLAConditionException;

	public WorkflowMetricsSLACondition updateImpl(
		WorkflowMetricsSLACondition workflowMetricsSLACondition);

	/**
	 * Returns the workflow metrics sla condition with the primary key or throws a <code>NoSuchSLAConditionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition
	 * @throws NoSuchSLAConditionException if a workflow metrics sla condition with the primary key could not be found
	 */
	public WorkflowMetricsSLACondition findByPrimaryKey(
			long workflowMetricsSLAConditionId)
		throws NoSuchSLAConditionException;

	/**
	 * Returns the workflow metrics sla condition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition, or <code>null</code> if a workflow metrics sla condition with the primary key could not be found
	 */
	public WorkflowMetricsSLACondition fetchByPrimaryKey(
		long workflowMetricsSLAConditionId);

	/**
	 * Returns all the workflow metrics sla conditions.
	 *
	 * @return the workflow metrics sla conditions
	 */
	public java.util.List<WorkflowMetricsSLACondition> findAll();

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
	public java.util.List<WorkflowMetricsSLACondition> findAll(
		int start, int end);

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
	public java.util.List<WorkflowMetricsSLACondition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLACondition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACondition> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the workflow metrics sla conditions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of workflow metrics sla conditions.
	 *
	 * @return the number of workflow metrics sla conditions
	 */
	public int countAll();

}