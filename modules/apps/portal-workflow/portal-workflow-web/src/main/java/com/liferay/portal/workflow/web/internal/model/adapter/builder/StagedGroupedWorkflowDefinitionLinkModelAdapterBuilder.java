/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
@Component(immediate = true, service = ModelAdapterBuilder.class)
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