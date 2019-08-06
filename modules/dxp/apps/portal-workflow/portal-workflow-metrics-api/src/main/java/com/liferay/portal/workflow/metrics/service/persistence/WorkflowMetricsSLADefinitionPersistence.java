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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the workflow metrics sla definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionUtil
 * @generated
 */
@ProviderType
public interface WorkflowMetricsSLADefinitionPersistence
	extends BasePersistence<WorkflowMetricsSLADefinition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WorkflowMetricsSLADefinitionUtil} to access the workflow metrics sla definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the workflow metrics sla definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla definitions
	 */
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid(String uuid);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

	/**
	 * Returns the workflow metrics sla definitions before and after the current workflow metrics sla definition in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the current workflow metrics sla definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinition[] findByUuid_PrevAndNext(
			long workflowMetricsSLADefinitionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Removes all the workflow metrics sla definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla definitions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByUUID_G(String uuid, long groupId)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the workflow metrics sla definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the workflow metrics sla definition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla definition that was removed
	 */
	public WorkflowMetricsSLADefinition removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla definitions
	 */
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public WorkflowMetricsSLADefinition[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLADefinitionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Removes all the workflow metrics sla definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of workflow metrics sla definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definitions
	 */
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P(
		long companyId, long processId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByC_P_First(
			long companyId, long processId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByC_P_First(
		long companyId, long processId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByC_P_Last(
			long companyId, long processId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByC_P_Last(
		long companyId, long processId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public WorkflowMetricsSLADefinition[] findByC_P_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 */
	public void removeByC_P(long companyId, long processId);

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public int countByC_P(long companyId, long processId);

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition findByC_N_P(
			long companyId, String name, long processId)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByC_N_P(
		long companyId, String name, long processId);

	/**
	 * Returns the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByC_N_P(
		long companyId, String name, long processId, boolean useFinderCache);

	/**
	 * Removes the workflow metrics sla definition where companyId = &#63; and name = &#63; and processId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the workflow metrics sla definition that was removed
	 */
	public WorkflowMetricsSLADefinition removeByC_N_P(
			long companyId, String name, long processId)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and name = &#63; and processId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param processId the process ID
	 * @return the number of matching workflow metrics sla definitions
	 */
	public int countByC_N_P(long companyId, String name, long processId);

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_S(
		long companyId, long processId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache);

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
	public WorkflowMetricsSLADefinition findByC_P_S_First(
			long companyId, long processId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the first workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByC_P_S_First(
		long companyId, long processId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public WorkflowMetricsSLADefinition findByC_P_S_Last(
			long companyId, long processId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the last workflow metrics sla definition in the ordered set where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByC_P_S_Last(
		long companyId, long processId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public WorkflowMetricsSLADefinition[] findByC_P_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 */
	public void removeByC_P_S(long companyId, long processId, int status);

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	public int countByC_P_S(long companyId, long processId, int status);

	/**
	 * Returns all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @return the matching workflow metrics sla definitions
	 */
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinition> findByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache);

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
	public WorkflowMetricsSLADefinition findByC_P_NotPV_S_First(
			long companyId, long processId, String processVersion, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

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
	public WorkflowMetricsSLADefinition fetchByC_P_NotPV_S_First(
		long companyId, long processId, String processVersion, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public WorkflowMetricsSLADefinition findByC_P_NotPV_S_Last(
			long companyId, long processId, String processVersion, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

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
	public WorkflowMetricsSLADefinition fetchByC_P_NotPV_S_Last(
		long companyId, long processId, String processVersion, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public WorkflowMetricsSLADefinition[] findByC_P_NotPV_S_PrevAndNext(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			String processVersion, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinition> orderByComparator)
		throws NoSuchSLADefinitionException;

	/**
	 * Removes all the workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 */
	public void removeByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status);

	/**
	 * Returns the number of workflow metrics sla definitions where companyId = &#63; and processId = &#63; and processVersion &ne; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param processId the process ID
	 * @param processVersion the process version
	 * @param status the status
	 * @return the number of matching workflow metrics sla definitions
	 */
	public int countByC_P_NotPV_S(
		long companyId, long processId, String processVersion, int status);

	/**
	 * Caches the workflow metrics sla definition in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 */
	public void cacheResult(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition);

	/**
	 * Caches the workflow metrics sla definitions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitions the workflow metrics sla definitions
	 */
	public void cacheResult(
		java.util.List<WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitions);

	/**
	 * Creates a new workflow metrics sla definition with the primary key. Does not add the workflow metrics sla definition to the database.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key for the new workflow metrics sla definition
	 * @return the new workflow metrics sla definition
	 */
	public WorkflowMetricsSLADefinition create(
		long workflowMetricsSLADefinitionId);

	/**
	 * Removes the workflow metrics sla definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinition remove(
			long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionException;

	public WorkflowMetricsSLADefinition updateImpl(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition);

	/**
	 * Returns the workflow metrics sla definition with the primary key or throws a <code>NoSuchSLADefinitionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition
	 * @throws NoSuchSLADefinitionException if a workflow metrics sla definition with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinition findByPrimaryKey(
			long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionException;

	/**
	 * Returns the workflow metrics sla definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition, or <code>null</code> if a workflow metrics sla definition with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinition fetchByPrimaryKey(
		long workflowMetricsSLADefinitionId);

	/**
	 * Returns all the workflow metrics sla definitions.
	 *
	 * @return the workflow metrics sla definitions
	 */
	public java.util.List<WorkflowMetricsSLADefinition> findAll();

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
	public java.util.List<WorkflowMetricsSLADefinition> findAll(
		int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinition> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the workflow metrics sla definitions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of workflow metrics sla definitions.
	 *
	 * @return the number of workflow metrics sla definitions
	 */
	public int countAll();

}