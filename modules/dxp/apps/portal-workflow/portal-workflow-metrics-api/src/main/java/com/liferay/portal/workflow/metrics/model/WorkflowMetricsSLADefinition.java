/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the WorkflowMetricsSLADefinition service. Represents a row in the &quot;WMSLADefinition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionImpl"
)
@ProviderType
public interface WorkflowMetricsSLADefinition
	extends PersistedModel, WorkflowMetricsSLADefinitionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WorkflowMetricsSLADefinition, Long>
		WORKFLOW_METRICS_SLA_DEFINITION_ID_ACCESSOR =
			new Accessor<WorkflowMetricsSLADefinition, Long>() {

				@Override
				public Long get(
					WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

					return workflowMetricsSLADefinition.
						getWorkflowMetricsSLADefinitionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<WorkflowMetricsSLADefinition> getTypeClass() {
					return WorkflowMetricsSLADefinition.class;
				}

			};

}