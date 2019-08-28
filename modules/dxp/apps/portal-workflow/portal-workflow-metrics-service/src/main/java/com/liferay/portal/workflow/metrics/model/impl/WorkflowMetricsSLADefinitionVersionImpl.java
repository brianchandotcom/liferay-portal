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

package com.liferay.portal.workflow.metrics.model.impl;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model implementation for the WorkflowMetricsSLADefinitionVersion
 * service. Represents a row in the &quot;WMSLADefinitionVersion&quot; database
 * table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the
 * <code>com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion</code>
 * interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class WorkflowMetricsSLADefinitionVersionImpl
	extends WorkflowMetricsSLADefinitionVersionBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a workflow
	 * metrics sla definition version model instance should use the {@link
	 * com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion}
	 * interface instead.
	 */
	public WorkflowMetricsSLADefinitionVersionImpl() {
	}

}