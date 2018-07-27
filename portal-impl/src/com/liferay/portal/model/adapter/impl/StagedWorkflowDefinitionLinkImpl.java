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

package com.liferay.portal.model.adapter.impl;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.adapter.StagedWorkflowDefinitionLink;
import com.liferay.portal.model.impl.WorkflowDefinitionLinkImpl;

/**
 * @author Zoltan Csaszi
 */
public class StagedWorkflowDefinitionLinkImpl
	extends WorkflowDefinitionLinkImpl implements StagedWorkflowDefinitionLink {

	public StagedWorkflowDefinitionLinkImpl(
		WorkflowDefinitionLink workflowDefinitionLink) {

		_workflowDefinitionLink = workflowDefinitionLink;
	}

	@Override
	public Object clone() {
		return new StagedWorkflowDefinitionLinkImpl(_workflowDefinitionLink);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedWorkflowDefinitionLink.class);
	}

	@Override
	public String getUuid() {
		return "";
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	private final WorkflowDefinitionLink _workflowDefinitionLink;

}