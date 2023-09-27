/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.model.adapter.builder;

import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.adapter.StagedGroupedWorkflowDefinitionLink;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portal.model.adapter.impl.StagedGroupedWorkflowDefinitionLinkImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Zoltan Csaszi
 */
@Component(service = ModelAdapterBuilder.class)
public class StagedGroupedWorkflowDefinitionLinkModelAdapterBuilder
	implements ModelAdapterBuilder
		<WorkflowDefinitionLink, StagedGroupedWorkflowDefinitionLink> {

	@Override
	public StagedGroupedWorkflowDefinitionLink build(
		WorkflowDefinitionLink workflowDefinitionLink) {

		return new StagedGroupedWorkflowDefinitionLinkImpl(
			workflowDefinitionLink);
	}

}