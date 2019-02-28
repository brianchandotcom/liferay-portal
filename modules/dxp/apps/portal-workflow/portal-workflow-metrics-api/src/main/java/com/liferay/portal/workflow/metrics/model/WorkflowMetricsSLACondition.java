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
 * The extended model interface for the WorkflowMetricsSLACondition service. Represents a row in the &quot;WorkflowMetricsSLACondition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLAConditionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionImpl"
)
@ProviderType
public interface WorkflowMetricsSLACondition
	extends WorkflowMetricsSLAConditionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WorkflowMetricsSLACondition, Long>
		WORKFLOW_METRICS_SLA_CONDITION_ID_ACCESSOR =
			new Accessor<WorkflowMetricsSLACondition, Long>() {

				@Override
				public Long get(
					WorkflowMetricsSLACondition workflowMetricsSLACondition) {

					return workflowMetricsSLACondition.
						getWorkflowMetricsSLAConditionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<WorkflowMetricsSLACondition> getTypeClass() {
					return WorkflowMetricsSLACondition.class;
				}

			};

}