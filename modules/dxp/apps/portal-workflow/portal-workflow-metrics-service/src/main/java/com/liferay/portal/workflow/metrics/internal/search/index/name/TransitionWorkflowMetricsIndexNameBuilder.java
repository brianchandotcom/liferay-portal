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

package com.liferay.portal.workflow.metrics.internal.search.index.name;

import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "workflow.metrics.index.entity.name=transition",
	service = WorkflowMetricsIndexNameBuilder.class
)
public class TransitionWorkflowMetricsIndexNameBuilder
	extends BaseWorkfllowMetricsIndexNameBuilder {

	@Override
	public String getIndexNamePrefix() {
		return _INDEX_NAME_PREFIX;
	}

	private static final String _INDEX_NAME_PREFIX =
		"workflow-metrics-transitions-";

}