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

package com.liferay.portal.workflow.metrics.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the WorkflowMetricsSLACalendar service. Represents a row in the &quot;WorkflowMetricsSLACalendar&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACalendarModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarImpl"
)
@ProviderType
public interface WorkflowMetricsSLACalendar
	extends WorkflowMetricsSLACalendarModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WorkflowMetricsSLACalendar, Long>
		WORKFLOW_METRICS_SLA_CALENDAR_ID_ACCESSOR =
			new Accessor<WorkflowMetricsSLACalendar, Long>() {

				@Override
				public Long get(
					WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

					return workflowMetricsSLACalendar.
						getWorkflowMetricsSLACalendarId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<WorkflowMetricsSLACalendar> getTypeClass() {
					return WorkflowMetricsSLACalendar.class;
				}

			};

}