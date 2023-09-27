/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionVersionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the workflow metrics sla definition version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionVersionUtil
 * @generated
 */
@ProviderType
public interface WorkflowMetricsSLADefinitionVersionPersistence
	extends BasePersistence<WorkflowMetricsSLADefinitionVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WorkflowMetricsSLADefinitionVersionUtil} to access the workflow metrics sla definition version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla definition versions
	 */
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator);

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator);

	/**
	 * Returns the workflow metrics sla definition versions before and after the current workflow metrics sla definition version in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the current workflow metrics sla definition version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion[] findByUuid_PrevAndNext(
			long workflowMetricsSLADefinitionVersionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Removes all the workflow metrics sla definition versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion findByUUID_G(
			String uuid, long groupId)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the workflow metrics sla definition version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla definition version that was removed
	 */
	public WorkflowMetricsSLADefinitionVersion removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla definition versions
	 */
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator);

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator);

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
	public WorkflowMetricsSLADefinitionVersion[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLADefinitionVersionId, String uuid,
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Removes all the workflow metrics sla definition versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of workflow metrics sla definition versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition versions
	 */
	public java.util.List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion>
		findByWorkflowMetricsSLADefinitionId(
			long workflowMetricsSLADefinitionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion
			findByWorkflowMetricsSLADefinitionId_First(
				long workflowMetricsSLADefinitionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the first workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion
		fetchByWorkflowMetricsSLADefinitionId_First(
			long workflowMetricsSLADefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator);

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion
			findByWorkflowMetricsSLADefinitionId_Last(
				long workflowMetricsSLADefinitionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the last workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion
		fetchByWorkflowMetricsSLADefinitionId_Last(
			long workflowMetricsSLADefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLADefinitionVersion> orderByComparator);

	/**
	 * Returns the workflow metrics sla definition versions before and after the current workflow metrics sla definition version in the ordered set where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the current workflow metrics sla definition version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion[]
			findByWorkflowMetricsSLADefinitionId_PrevAndNext(
				long workflowMetricsSLADefinitionVersionId,
				long workflowMetricsSLADefinitionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<WorkflowMetricsSLADefinitionVersion> orderByComparator)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Removes all the workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 */
	public void removeByWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId);

	/**
	 * Returns the number of workflow metrics sla definition versions where workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public int countByWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId);

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion findByV_WMSLAD(
			String version, long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId);

	/**
	 * Returns the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId,
		boolean useFinderCache);

	/**
	 * Removes the workflow metrics sla definition version where version = &#63; and workflowMetricsSLADefinitionId = &#63; from the database.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the workflow metrics sla definition version that was removed
	 */
	public WorkflowMetricsSLADefinitionVersion removeByV_WMSLAD(
			String version, long workflowMetricsSLADefinitionId)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the number of workflow metrics sla definition versions where version = &#63; and workflowMetricsSLADefinitionId = &#63;.
	 *
	 * @param version the version
	 * @param workflowMetricsSLADefinitionId the workflow metrics sla definition ID
	 * @return the number of matching workflow metrics sla definition versions
	 */
	public int countByV_WMSLAD(
		String version, long workflowMetricsSLADefinitionId);

	/**
	 * Caches the workflow metrics sla definition version in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitionVersion the workflow metrics sla definition version
	 */
	public void cacheResult(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion);

	/**
	 * Caches the workflow metrics sla definition versions in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLADefinitionVersions the workflow metrics sla definition versions
	 */
	public void cacheResult(
		java.util.List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions);

	/**
	 * Creates a new workflow metrics sla definition version with the primary key. Does not add the workflow metrics sla definition version to the database.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key for the new workflow metrics sla definition version
	 * @return the new workflow metrics sla definition version
	 */
	public WorkflowMetricsSLADefinitionVersion create(
		long workflowMetricsSLADefinitionVersionId);

	/**
	 * Removes the workflow metrics sla definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was removed
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion remove(
			long workflowMetricsSLADefinitionVersionId)
		throws NoSuchSLADefinitionVersionException;

	public WorkflowMetricsSLADefinitionVersion updateImpl(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion);

	/**
	 * Returns the workflow metrics sla definition version with the primary key or throws a <code>NoSuchSLADefinitionVersionException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version
	 * @throws NoSuchSLADefinitionVersionException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion findByPrimaryKey(
			long workflowMetricsSLADefinitionVersionId)
		throws NoSuchSLADefinitionVersionException;

	/**
	 * Returns the workflow metrics sla definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version, or <code>null</code> if a workflow metrics sla definition version with the primary key could not be found
	 */
	public WorkflowMetricsSLADefinitionVersion fetchByPrimaryKey(
		long workflowMetricsSLADefinitionVersionId);

	/**
	 * Returns all the workflow metrics sla definition versions.
	 *
	 * @return the workflow metrics sla definition versions
	 */
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findAll();

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator);

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
	public java.util.List<WorkflowMetricsSLADefinitionVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLADefinitionVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the workflow metrics sla definition versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of workflow metrics sla definition versions.
	 *
	 * @return the number of workflow metrics sla definition versions
	 */
	public int countAll();

}