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

package com.liferay.portal.workflow.metrics.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.workflow.metrics.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.portal.workflow.metrics.rest.internal.graphql.query.v1_0.Query;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.AssigneeMetricResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.AssigneeResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.CalendarResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.HistogramMetricResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.IndexResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.InstanceResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.NodeMetricResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.NodeResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.ProcessMetricResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.ProcessResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.ProcessVersionResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.ReindexStatusResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.RoleResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.SLAResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.SLAResultResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.TaskResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.TimeRangeResourceImpl;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.AssigneeMetricResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.AssigneeResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.CalendarResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.HistogramMetricResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.IndexResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.NodeMetricResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.NodeResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessMetricResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessVersionResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ReindexStatusResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.RoleResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResultResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TimeRangeResource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAssigneeResourceComponentServiceObjects(
			_assigneeResourceComponentServiceObjects);
		Mutation.setAssigneeMetricResourceComponentServiceObjects(
			_assigneeMetricResourceComponentServiceObjects);
		Mutation.setIndexResourceComponentServiceObjects(
			_indexResourceComponentServiceObjects);
		Mutation.setInstanceResourceComponentServiceObjects(
			_instanceResourceComponentServiceObjects);
		Mutation.setNodeResourceComponentServiceObjects(
			_nodeResourceComponentServiceObjects);
		Mutation.setProcessResourceComponentServiceObjects(
			_processResourceComponentServiceObjects);
		Mutation.setSLAResourceComponentServiceObjects(
			_slaResourceComponentServiceObjects);
		Mutation.setTaskResourceComponentServiceObjects(
			_taskResourceComponentServiceObjects);

		Query.setCalendarResourceComponentServiceObjects(
			_calendarResourceComponentServiceObjects);
		Query.setHistogramMetricResourceComponentServiceObjects(
			_histogramMetricResourceComponentServiceObjects);
		Query.setIndexResourceComponentServiceObjects(
			_indexResourceComponentServiceObjects);
		Query.setInstanceResourceComponentServiceObjects(
			_instanceResourceComponentServiceObjects);
		Query.setNodeResourceComponentServiceObjects(
			_nodeResourceComponentServiceObjects);
		Query.setNodeMetricResourceComponentServiceObjects(
			_nodeMetricResourceComponentServiceObjects);
		Query.setProcessResourceComponentServiceObjects(
			_processResourceComponentServiceObjects);
		Query.setProcessMetricResourceComponentServiceObjects(
			_processMetricResourceComponentServiceObjects);
		Query.setProcessVersionResourceComponentServiceObjects(
			_processVersionResourceComponentServiceObjects);
		Query.setReindexStatusResourceComponentServiceObjects(
			_reindexStatusResourceComponentServiceObjects);
		Query.setRoleResourceComponentServiceObjects(
			_roleResourceComponentServiceObjects);
		Query.setSLAResourceComponentServiceObjects(
			_slaResourceComponentServiceObjects);
		Query.setSLAResultResourceComponentServiceObjects(
			_slaResultResourceComponentServiceObjects);
		Query.setTaskResourceComponentServiceObjects(
			_taskResourceComponentServiceObjects);
		Query.setTimeRangeResourceComponentServiceObjects(
			_timeRangeResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Portal.Workflow.Metrics.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/portal-workflow-metrics-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#createProcessAssigneesPage",
			new ObjectValuePair<>(
				AssigneeResourceImpl.class, "postProcessAssigneesPage"));
		_resourceMethodPairs.put(
			"mutation#createProcessAssigneeMetricsPage",
			new ObjectValuePair<>(
				AssigneeMetricResourceImpl.class,
				"postProcessAssigneeMetricsPage"));
		_resourceMethodPairs.put(
			"mutation#patchIndexRefresh",
			new ObjectValuePair<>(
				IndexResourceImpl.class, "patchIndexRefresh"));
		_resourceMethodPairs.put(
			"mutation#patchIndexReindex",
			new ObjectValuePair<>(
				IndexResourceImpl.class, "patchIndexReindex"));
		_resourceMethodPairs.put(
			"mutation#createProcessInstance",
			new ObjectValuePair<>(
				InstanceResourceImpl.class, "postProcessInstance"));
		_resourceMethodPairs.put(
			"mutation#createProcessInstanceBatch",
			new ObjectValuePair<>(
				InstanceResourceImpl.class, "postProcessInstanceBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProcessInstance",
			new ObjectValuePair<>(
				InstanceResourceImpl.class, "deleteProcessInstance"));
		_resourceMethodPairs.put(
			"mutation#patchProcessInstance",
			new ObjectValuePair<>(
				InstanceResourceImpl.class, "patchProcessInstance"));
		_resourceMethodPairs.put(
			"mutation#patchProcessInstanceComplete",
			new ObjectValuePair<>(
				InstanceResourceImpl.class, "patchProcessInstanceComplete"));
		_resourceMethodPairs.put(
			"mutation#createProcessNode",
			new ObjectValuePair<>(NodeResourceImpl.class, "postProcessNode"));
		_resourceMethodPairs.put(
			"mutation#createProcessNodeBatch",
			new ObjectValuePair<>(
				NodeResourceImpl.class, "postProcessNodeBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProcessNode",
			new ObjectValuePair<>(NodeResourceImpl.class, "deleteProcessNode"));
		_resourceMethodPairs.put(
			"mutation#createProcess",
			new ObjectValuePair<>(ProcessResourceImpl.class, "postProcess"));
		_resourceMethodPairs.put(
			"mutation#createProcessBatch",
			new ObjectValuePair<>(
				ProcessResourceImpl.class, "postProcessBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProcess",
			new ObjectValuePair<>(ProcessResourceImpl.class, "deleteProcess"));
		_resourceMethodPairs.put(
			"mutation#deleteProcessBatch",
			new ObjectValuePair<>(
				ProcessResourceImpl.class, "deleteProcessBatch"));
		_resourceMethodPairs.put(
			"mutation#updateProcess",
			new ObjectValuePair<>(ProcessResourceImpl.class, "putProcess"));
		_resourceMethodPairs.put(
			"mutation#updateProcessBatch",
			new ObjectValuePair<>(
				ProcessResourceImpl.class, "putProcessBatch"));
		_resourceMethodPairs.put(
			"mutation#createProcessSLA",
			new ObjectValuePair<>(SLAResourceImpl.class, "postProcessSLA"));
		_resourceMethodPairs.put(
			"mutation#createProcessSLABatch",
			new ObjectValuePair<>(
				SLAResourceImpl.class, "postProcessSLABatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSLA",
			new ObjectValuePair<>(SLAResourceImpl.class, "deleteSLA"));
		_resourceMethodPairs.put(
			"mutation#deleteSLABatch",
			new ObjectValuePair<>(SLAResourceImpl.class, "deleteSLABatch"));
		_resourceMethodPairs.put(
			"mutation#updateSLA",
			new ObjectValuePair<>(SLAResourceImpl.class, "putSLA"));
		_resourceMethodPairs.put(
			"mutation#updateSLABatch",
			new ObjectValuePair<>(SLAResourceImpl.class, "putSLABatch"));
		_resourceMethodPairs.put(
			"mutation#createProcessTask",
			new ObjectValuePair<>(TaskResourceImpl.class, "postProcessTask"));
		_resourceMethodPairs.put(
			"mutation#createProcessTaskBatch",
			new ObjectValuePair<>(
				TaskResourceImpl.class, "postProcessTaskBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProcessTask",
			new ObjectValuePair<>(TaskResourceImpl.class, "deleteProcessTask"));
		_resourceMethodPairs.put(
			"mutation#patchProcessTask",
			new ObjectValuePair<>(TaskResourceImpl.class, "patchProcessTask"));
		_resourceMethodPairs.put(
			"mutation#patchProcessTaskComplete",
			new ObjectValuePair<>(
				TaskResourceImpl.class, "patchProcessTaskComplete"));
		_resourceMethodPairs.put(
			"mutation#createTasksPage",
			new ObjectValuePair<>(TaskResourceImpl.class, "postTasksPage"));
		_resourceMethodPairs.put(
			"query#calendars",
			new ObjectValuePair<>(
				CalendarResourceImpl.class, "getCalendarsPage"));
		_resourceMethodPairs.put(
			"query#processHistogramMetric",
			new ObjectValuePair<>(
				HistogramMetricResourceImpl.class,
				"getProcessHistogramMetric"));
		_resourceMethodPairs.put(
			"query#indexes",
			new ObjectValuePair<>(IndexResourceImpl.class, "getIndexesPage"));
		_resourceMethodPairs.put(
			"query#processInstances",
			new ObjectValuePair<>(
				InstanceResourceImpl.class, "getProcessInstancesPage"));
		_resourceMethodPairs.put(
			"query#processInstance",
			new ObjectValuePair<>(
				InstanceResourceImpl.class, "getProcessInstance"));
		_resourceMethodPairs.put(
			"query#processNodes",
			new ObjectValuePair<>(
				NodeResourceImpl.class, "getProcessNodesPage"));
		_resourceMethodPairs.put(
			"query#processNodeMetrics",
			new ObjectValuePair<>(
				NodeMetricResourceImpl.class, "getProcessNodeMetricsPage"));
		_resourceMethodPairs.put(
			"query#process",
			new ObjectValuePair<>(ProcessResourceImpl.class, "getProcess"));
		_resourceMethodPairs.put(
			"query#processTitle",
			new ObjectValuePair<>(
				ProcessResourceImpl.class, "getProcessTitle"));
		_resourceMethodPairs.put(
			"query#processMetrics",
			new ObjectValuePair<>(
				ProcessMetricResourceImpl.class, "getProcessMetricsPage"));
		_resourceMethodPairs.put(
			"query#processMetric",
			new ObjectValuePair<>(
				ProcessMetricResourceImpl.class, "getProcessMetric"));
		_resourceMethodPairs.put(
			"query#processProcessVersions",
			new ObjectValuePair<>(
				ProcessVersionResourceImpl.class,
				"getProcessProcessVersionsPage"));
		_resourceMethodPairs.put(
			"query#reindexStatuses",
			new ObjectValuePair<>(
				ReindexStatusResourceImpl.class, "getReindexStatusesPage"));
		_resourceMethodPairs.put(
			"query#processRoles",
			new ObjectValuePair<>(
				RoleResourceImpl.class, "getProcessRolesPage"));
		_resourceMethodPairs.put(
			"query#processSLAs",
			new ObjectValuePair<>(SLAResourceImpl.class, "getProcessSLAsPage"));
		_resourceMethodPairs.put(
			"query#sLA",
			new ObjectValuePair<>(SLAResourceImpl.class, "getSLA"));
		_resourceMethodPairs.put(
			"query#processLastSLAResult",
			new ObjectValuePair<>(
				SLAResultResourceImpl.class, "getProcessLastSLAResult"));
		_resourceMethodPairs.put(
			"query#processTasks",
			new ObjectValuePair<>(
				TaskResourceImpl.class, "getProcessTasksPage"));
		_resourceMethodPairs.put(
			"query#processTask",
			new ObjectValuePair<>(TaskResourceImpl.class, "getProcessTask"));
		_resourceMethodPairs.put(
			"query#timeRanges",
			new ObjectValuePair<>(
				TimeRangeResourceImpl.class, "getTimeRangesPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AssigneeResource>
		_assigneeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AssigneeMetricResource>
		_assigneeMetricResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<IndexResource>
		_indexResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<InstanceResource>
		_instanceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<NodeResource>
		_nodeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProcessResource>
		_processResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SLAResource>
		_slaResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaskResource>
		_taskResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CalendarResource>
		_calendarResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<HistogramMetricResource>
		_histogramMetricResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<NodeMetricResource>
		_nodeMetricResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProcessMetricResource>
		_processMetricResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProcessVersionResource>
		_processVersionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ReindexStatusResource>
		_reindexStatusResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<RoleResource>
		_roleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SLAResultResource>
		_slaResultResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TimeRangeResource>
		_timeRangeResourceComponentServiceObjects;

}