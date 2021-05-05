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

import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.workflow.metrics.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.portal.workflow.metrics.rest.internal.graphql.query.v1_0.Query;
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
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ReindexStatusResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.RoleResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResultResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TimeRangeResource;

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
@Component(immediate = true, service = ServletData.class)
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