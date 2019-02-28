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
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the workflow metrics sla calendar service. This utility wraps <code>com.liferay.portal.workflow.metrics.service.persistence.impl.WorkflowMetricsSLACalendarPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACalendarPersistence
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLACalendarUtil {

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
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		getPersistence().clearCache(workflowMetricsSLACalendar);
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
	public static Map<Serializable, WorkflowMetricsSLACalendar>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WorkflowMetricsSLACalendar> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WorkflowMetricsSLACalendar> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WorkflowMetricsSLACalendar> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WorkflowMetricsSLACalendar update(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		return getPersistence().update(workflowMetricsSLACalendar);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WorkflowMetricsSLACalendar update(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
		ServiceContext serviceContext) {

		return getPersistence().update(
			workflowMetricsSLACalendar, serviceContext);
	}

	/**
	 * Returns all the workflow metrics sla calendars where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching workflow metrics sla calendars
	 */
	public static List<WorkflowMetricsSLACalendar> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<WorkflowMetricsSLACalendar> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar findByUuid_First(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar fetchByUuid_First(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar findByUuid_Last(
			String uuid,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar fetchByUuid_Last(
		String uuid,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the workflow metrics sla calendars before and after the current workflow metrics sla calendar in the ordered set where uuid = &#63;.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the current workflow metrics sla calendar
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	public static WorkflowMetricsSLACalendar[] findByUuid_PrevAndNext(
			long workflowMetricsSLACalendarId, String uuid,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByUuid_PrevAndNext(
			workflowMetricsSLACalendarId, uuid, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla calendars where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching workflow metrics sla calendars
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSLACalendarException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	 * Removes the workflow metrics sla calendar where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the workflow metrics sla calendar that was removed
	 */
	public static WorkflowMetricsSLACalendar removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching workflow metrics sla calendars
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching workflow metrics sla calendars
	 */
	public static List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<WorkflowMetricsSLACalendar> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last workflow metrics sla calendar in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	public static WorkflowMetricsSLACalendar fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static WorkflowMetricsSLACalendar[] findByUuid_C_PrevAndNext(
			long workflowMetricsSLACalendarId, String uuid, long companyId,
			OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByUuid_C_PrevAndNext(
			workflowMetricsSLACalendarId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the workflow metrics sla calendars where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of workflow metrics sla calendars where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching workflow metrics sla calendars
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the workflow metrics sla calendar in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACalendar the workflow metrics sla calendar
	 */
	public static void cacheResult(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		getPersistence().cacheResult(workflowMetricsSLACalendar);
	}

	/**
	 * Caches the workflow metrics sla calendars in the entity cache if it is enabled.
	 *
	 * @param workflowMetricsSLACalendars the workflow metrics sla calendars
	 */
	public static void cacheResult(
		List<WorkflowMetricsSLACalendar> workflowMetricsSLACalendars) {

		getPersistence().cacheResult(workflowMetricsSLACalendars);
	}

	/**
	 * Creates a new workflow metrics sla calendar with the primary key. Does not add the workflow metrics sla calendar to the database.
	 *
	 * @param workflowMetricsSLACalendarId the primary key for the new workflow metrics sla calendar
	 * @return the new workflow metrics sla calendar
	 */
	public static WorkflowMetricsSLACalendar create(
		long workflowMetricsSLACalendarId) {

		return getPersistence().create(workflowMetricsSLACalendarId);
	}

	/**
	 * Removes the workflow metrics sla calendar with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was removed
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	public static WorkflowMetricsSLACalendar remove(
			long workflowMetricsSLACalendarId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().remove(workflowMetricsSLACalendarId);
	}

	public static WorkflowMetricsSLACalendar updateImpl(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		return getPersistence().updateImpl(workflowMetricsSLACalendar);
	}

	/**
	 * Returns the workflow metrics sla calendar with the primary key or throws a <code>NoSuchSLACalendarException</code> if it could not be found.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar
	 * @throws NoSuchSLACalendarException if a workflow metrics sla calendar with the primary key could not be found
	 */
	public static WorkflowMetricsSLACalendar findByPrimaryKey(
			long workflowMetricsSLACalendarId)
		throws com.liferay.portal.workflow.metrics.exception.
			NoSuchSLACalendarException {

		return getPersistence().findByPrimaryKey(workflowMetricsSLACalendarId);
	}

	/**
	 * Returns the workflow metrics sla calendar with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar, or <code>null</code> if a workflow metrics sla calendar with the primary key could not be found
	 */
	public static WorkflowMetricsSLACalendar fetchByPrimaryKey(
		long workflowMetricsSLACalendarId) {

		return getPersistence().fetchByPrimaryKey(workflowMetricsSLACalendarId);
	}

	/**
	 * Returns all the workflow metrics sla calendars.
	 *
	 * @return the workflow metrics sla calendars
	 */
	public static List<WorkflowMetricsSLACalendar> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<WorkflowMetricsSLACalendar> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<WorkflowMetricsSLACalendar> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<WorkflowMetricsSLACalendar> findAll(
		int start, int end,
		OrderByComparator<WorkflowMetricsSLACalendar> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the workflow metrics sla calendars from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of workflow metrics sla calendars.
	 *
	 * @return the number of workflow metrics sla calendars
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WorkflowMetricsSLACalendarPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WorkflowMetricsSLACalendarPersistence,
		 WorkflowMetricsSLACalendarPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowMetricsSLACalendarPersistence.class);

		ServiceTracker
			<WorkflowMetricsSLACalendarPersistence,
			 WorkflowMetricsSLACalendarPersistence> serviceTracker =
				new ServiceTracker
					<WorkflowMetricsSLACalendarPersistence,
					 WorkflowMetricsSLACalendarPersistence>(
						 bundle.getBundleContext(),
						 WorkflowMetricsSLACalendarPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}