/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.workflow.metrics.internal.search.constants.WorkflowMetricsIndexTypeConstants;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "workflow.metrics.index.entity.name=sla-instance-result",
	service = WorkflowMetricsIndex.class
)
public class SLAInstanceResultWorkflowMetricsIndexImpl
	extends WorkflowMetricsIndex {

	public SLAInstanceResultWorkflowMetricsIndexImpl() {
		super(
			WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
			WorkflowMetricsIndexTypeConstants.SLA_INSTANCE_RESULT_TYPE);
	}

}