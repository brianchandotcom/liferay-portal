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
 * The extended model interface for the WorkflowMetricsSLADefinitionVersion service. Represents a row in the &quot;WMSLADefinitionVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionImpl"
)
@ProviderType
public interface WorkflowMetricsSLADefinitionVersion
	extends PersistedModel, WorkflowMetricsSLADefinitionVersionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WorkflowMetricsSLADefinitionVersion, Long>
		WORKFLOW_METRICS_SLA_DEFINITION_VERSION_ID_ACCESSOR =
			new Accessor<WorkflowMetricsSLADefinitionVersion, Long>() {

				@Override
				public Long get(
					WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion) {

					return workflowMetricsSLADefinitionVersion.
						getWorkflowMetricsSLADefinitionVersionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<WorkflowMetricsSLADefinitionVersion>
					getTypeClass() {

					return WorkflowMetricsSLADefinitionVersion.class;
				}

			};

}