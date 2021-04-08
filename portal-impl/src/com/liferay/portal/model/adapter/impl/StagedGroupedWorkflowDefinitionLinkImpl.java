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
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.WorkflowDefinitionLinkWrapper;
import com.liferay.portal.kernel.model.adapter.StagedGroupedWorkflowDefinitionLink;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @author Zoltan Csaszi
 */
public class StagedGroupedWorkflowDefinitionLinkImpl
	extends WorkflowDefinitionLinkWrapper
	implements StagedGroupedWorkflowDefinitionLink {

	public StagedGroupedWorkflowDefinitionLinkImpl(
		WorkflowDefinitionLink workflowDefinitionLink) {

		super(Objects.requireNonNull(workflowDefinitionLink));
	}

	@Override
	public String getClassName() {
		ClassName className = ClassNameLocalServiceUtil.fetchClassName(
			getClassNameId());

		if (className != null) {
			return className.getClassName();
		}

		return null;
	}

	@Override
	public Date getLastPublishDate() {
		return null;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedGroupedWorkflowDefinitionLink.class);
	}

	@Override
	public String getUuid() {
		return String.valueOf(getWorkflowDefinitionLinkId());
	}

	public WorkflowDefinitionLink getWorkflowDefinitionLink() {
		return getWrappedModel();
	}

	@Override
	public void setLastPublishDate(Date date) {
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected WorkflowDefinitionLinkWrapper wrap(
		WorkflowDefinitionLink workflowDefinitionLink) {

		return new StagedGroupedWorkflowDefinitionLinkImpl(
			workflowDefinitionLink);
	}

}