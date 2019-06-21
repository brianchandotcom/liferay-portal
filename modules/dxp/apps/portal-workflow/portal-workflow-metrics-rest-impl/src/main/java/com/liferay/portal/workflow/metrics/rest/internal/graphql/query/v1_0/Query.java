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

package com.liferay.portal.workflow.metrics.rest.internal.graphql.query.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Calendar;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.TimeRange;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.CalendarResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.NodeResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TimeRangeResource;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Query {

	public static void setCalendarResourceComponentServiceObjects(
		ComponentServiceObjects<CalendarResource>
			calendarResourceComponentServiceObjects) {

		_calendarResourceComponentServiceObjects =
			calendarResourceComponentServiceObjects;
	}

	public static void setInstanceResourceComponentServiceObjects(
		ComponentServiceObjects<InstanceResource>
			instanceResourceComponentServiceObjects) {

		_instanceResourceComponentServiceObjects =
			instanceResourceComponentServiceObjects;
	}

	public static void setNodeResourceComponentServiceObjects(
		ComponentServiceObjects<NodeResource>
			nodeResourceComponentServiceObjects) {

		_nodeResourceComponentServiceObjects =
			nodeResourceComponentServiceObjects;
	}

	public static void setProcessResourceComponentServiceObjects(
		ComponentServiceObjects<ProcessResource>
			processResourceComponentServiceObjects) {

		_processResourceComponentServiceObjects =
			processResourceComponentServiceObjects;
	}

	public static void setSLAResourceComponentServiceObjects(
		ComponentServiceObjects<SLAResource>
			slaResourceComponentServiceObjects) {

		_slaResourceComponentServiceObjects =
			slaResourceComponentServiceObjects;
	}

	public static void setTaskResourceComponentServiceObjects(
		ComponentServiceObjects<TaskResource>
			taskResourceComponentServiceObjects) {

		_taskResourceComponentServiceObjects =
			taskResourceComponentServiceObjects;
	}

	public static void setTimeRangeResourceComponentServiceObjects(
		ComponentServiceObjects<TimeRangeResource>
			timeRangeResourceComponentServiceObjects) {

		_timeRangeResourceComponentServiceObjects =
			timeRangeResourceComponentServiceObjects;
	}

	@GraphQLField
	public java.util.Collection<Calendar> getCalendarsPage() throws Exception {
		return _applyComponentServiceObjects(
			_calendarResourceComponentServiceObjects,
			this::_populateResourceContext,
			calendarResource -> {
				Page paginationPage = calendarResource.getCalendarsPage();

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Instance> getProcessInstancesPage(
			@GraphQLName("processId") Long processId,
			@GraphQLName("slaStatuses") String[] slaStatuses,
			@GraphQLName("statuses") String[] statuses,
			@GraphQLName("taskKeys") String[] taskKeys,
			@GraphQLName("timeRange") Integer timeRange,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_instanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			instanceResource -> {
				Page paginationPage = instanceResource.getProcessInstancesPage(
					processId, slaStatuses, statuses, taskKeys, timeRange,
					Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Instance getProcessInstance(
			@GraphQLName("processId") Long processId,
			@GraphQLName("instanceId") Long instanceId)
		throws Exception {

		return _applyComponentServiceObjects(
			_instanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			instanceResource -> instanceResource.getProcessInstance(
				processId, instanceId));
	}

	@GraphQLField
	public java.util.Collection<Node> getProcessNodesPage(
			@GraphQLName("processId") Long processId)
		throws Exception {

		return _applyComponentServiceObjects(
			_nodeResourceComponentServiceObjects,
			this::_populateResourceContext,
			nodeResource -> {
				Page paginationPage = nodeResource.getProcessNodesPage(
					processId);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Process> getProcessesPage(
			@GraphQLName("title") String title,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> {
				Page paginationPage = processResource.getProcessesPage(
					title, Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Process getProcess(
			@GraphQLName("processId") Long processId,
			@GraphQLName("completed") Boolean completed,
			@GraphQLName("timeRange") Integer timeRange)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> processResource.getProcess(
				processId, completed, timeRange));
	}

	@GraphQLField
	public String getProcessTitle(@GraphQLName("processId") Long processId)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> processResource.getProcessTitle(processId));
	}

	@GraphQLField
	public java.util.Collection<SLA> getProcessSLAsPage(
			@GraphQLName("processId") Long processId,
			@GraphQLName("status") Integer status,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_slaResourceComponentServiceObjects, this::_populateResourceContext,
			slaResource -> {
				Page paginationPage = slaResource.getProcessSLAsPage(
					processId, status, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public SLA getSLA(@GraphQLName("slaId") Long slaId) throws Exception {
		return _applyComponentServiceObjects(
			_slaResourceComponentServiceObjects, this::_populateResourceContext,
			slaResource -> slaResource.getSLA(slaId));
	}

	@GraphQLField
	public java.util.Collection<Task> getProcessTasksPage(
			@GraphQLName("processId") Long processId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_taskResourceComponentServiceObjects,
			this::_populateResourceContext,
			taskResource -> {
				Page paginationPage = taskResource.getProcessTasksPage(
					processId, Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<TimeRange> getTimeRangesPage()
		throws Exception {

		return _applyComponentServiceObjects(
			_timeRangeResourceComponentServiceObjects,
			this::_populateResourceContext,
			timeRangeResource -> {
				Page paginationPage = timeRangeResource.getTimeRangesPage();

				return paginationPage.getItems();
			});
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(CalendarResource calendarResource)
		throws Exception {

		calendarResource.setContextAcceptLanguage(_acceptLanguage);
		calendarResource.setContextCompany(_company);
	}

	private void _populateResourceContext(InstanceResource instanceResource)
		throws Exception {

		instanceResource.setContextAcceptLanguage(_acceptLanguage);
		instanceResource.setContextCompany(_company);
	}

	private void _populateResourceContext(NodeResource nodeResource)
		throws Exception {

		nodeResource.setContextAcceptLanguage(_acceptLanguage);
		nodeResource.setContextCompany(_company);
	}

	private void _populateResourceContext(ProcessResource processResource)
		throws Exception {

		processResource.setContextAcceptLanguage(_acceptLanguage);
		processResource.setContextCompany(_company);
	}

	private void _populateResourceContext(SLAResource slaResource)
		throws Exception {

		slaResource.setContextAcceptLanguage(_acceptLanguage);
		slaResource.setContextCompany(_company);
	}

	private void _populateResourceContext(TaskResource taskResource)
		throws Exception {

		taskResource.setContextAcceptLanguage(_acceptLanguage);
		taskResource.setContextCompany(_company);
	}

	private void _populateResourceContext(TimeRangeResource timeRangeResource)
		throws Exception {

		timeRangeResource.setContextAcceptLanguage(_acceptLanguage);
		timeRangeResource.setContextCompany(_company);
	}

	private static ComponentServiceObjects<CalendarResource>
		_calendarResourceComponentServiceObjects;
	private static ComponentServiceObjects<InstanceResource>
		_instanceResourceComponentServiceObjects;
	private static ComponentServiceObjects<NodeResource>
		_nodeResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProcessResource>
		_processResourceComponentServiceObjects;
	private static ComponentServiceObjects<SLAResource>
		_slaResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaskResource>
		_taskResourceComponentServiceObjects;
	private static ComponentServiceObjects<TimeRangeResource>
		_timeRangeResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;

}