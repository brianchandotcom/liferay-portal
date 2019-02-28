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
import com.liferay.portal.workflow.metrics.exception.NoSuchSLACalendarException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar;

/**
 * The persistence interface for the workflow metrics sla calendar service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACalendarUtil
 * @generated
 */
@ProviderType
public interface WorkflowMetricsSLACalendarPersistence
	extends BasePersistence<WorkflowMetricsSLACalendar> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WorkflowMetricsSLACalendarUtil} to access the workflow metrics sla calendar persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid(String uuid);

	/**
	 * Returns a range of all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @return the range of matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator);

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException;

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator);

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException;

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator);

	/**
	 * Returns the workflow metrics sla calendars before and after the current workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the current workflow metrics sla calendar
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	public WorkflowMetricsSLACalendar[] findByUuid_PrevAndNext(
			long workflowMetricsSLACalendarId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException;

	/**
	 * Removes all the workflow metrics sla calendars where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla calendars
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLACalendarException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar findByUUID_G(String uuid, long groupId)
		throws NoSuchSLACalendarException;

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla calendar that was removed
	 */
	public WorkflowMetricsSLACalendar removeByUUID_G(String uuid, long groupId)
		throws NoSuchSLACalendarException;

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla calendars
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @return the range of matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator);

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException;

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator);

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException;

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public WorkflowMetricsSLACalendar fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator);

	/**
	 * Returns the workflow metrics sla calendars before and after the current workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the current workflow metrics sla calendar
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	public WorkflowMetricsSLACalendar[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLACalendarId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<WorkflowMetricsSLACalendar> orderByComparator)
		throws NoSuchSLACalendarException;

	/**
	 * Removes all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla calendars
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the workflow metrics sla calendar in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACalendar the workflow metrics sla calendar
	 */
	public void cacheResult(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar);

	/**
	 * Caches the workflow metrics sla calendars in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACalendars the workflow metrics sla calendars
	 */
	public void cacheResult(
		java.util.List<WorkflowMetricsSLACalendar> workflowMetricsSLACalendars);

	/**
	 * Creates a new workflow metrics sla calendar with the primary key. Does not add the workflow metrics sla calendar to the database.
	 *
	 * @param workflowMetricsSLACalendarId the primary key for the new workflow metrics sla calendar
	 * @return the new workflow metrics sla calendar
	 */
	public WorkflowMetricsSLACalendar create(long workflowMetricsSLACalendarId);

	/**
	 * Removes the workflow metrics sla calendar with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was removed
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	public WorkflowMetricsSLACalendar remove(long workflowMetricsSLACalendarId)
		throws NoSuchSLACalendarException;

	public WorkflowMetricsSLACalendar updateImpl(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar);

	/**
	 * Returns the workflow metrics sla calendar with the primary key or throws a <code>NoSuchSLACalendarException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	public WorkflowMetricsSLACalendar findByPrimaryKey(
			long workflowMetricsSLACalendarId)
		throws NoSuchSLACalendarException;

	/**
	 * Returns the workflow metrics sla calendar with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar, or <code>null</code> if a workflow metrics sla calendar with the primary key could not be found
	 */
	public WorkflowMetricsSLACalendar fetchByPrimaryKey(
		long workflowMetricsSLACalendarId);

	/**
	 * Returns all the workflow metrics sla calendars.
	 *
	 * @return the workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findAll();

	/**
	 * Returns a range of all the workflow metrics sla calendars.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @return the range of workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator);

	/**
	 * Returns an ordered range of all the workflow metrics sla calendars.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of workflow metrics sla calendars
	 */
	public java.util.List<WorkflowMetricsSLACalendar> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the workflow metrics sla calendars from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of workflow metrics sla calendars.
	 *
	 * @return the number of workflow metrics sla calendars
	 */
	public int countAll();

}