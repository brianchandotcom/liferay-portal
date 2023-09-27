/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Rafael Praxedes
 */
@ExtendedObjectClassDefinition(category = "workflow")
@Meta.OCD(
	id = "com.liferay.portal.workflow.metrics.internal.configuration.WorkflowMetricsConfiguration",
	localization = "content/Language",
	name = "workflow-metrics-configuration-name"
)
public interface WorkflowMetricsConfiguration {

	@Meta.AD(
		deflt = "10", description = "check-sla-job-interval-description",
		name = "check-sla-job-interval", required = false
	)
	public int checkSLAJobInterval();

	@Meta.AD(
		deflt = "10",
		description = "check-sla-definitions-job-interval-description",
		name = "check-sla-definitions-job-interval", required = false
	)
	public int checkSLADefinitionsJobInterval();

}