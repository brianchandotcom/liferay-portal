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
import com.liferay.portal.kernel.model.adapter.StagedGroupedWorkflowDefinitionLink;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.model.impl.WorkflowDefinitionLinkImpl;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Zoltan Csaszi
 */
public class StagedGroupedWorkflowDefinitionLinkImpl
	extends WorkflowDefinitionLinkImpl
	implements StagedGroupedWorkflowDefinitionLink {

	public StagedGroupedWorkflowDefinitionLinkImpl(
		WorkflowDefinitionLink workflowDefinitionLink) {

		_workflowDefinitionLink = workflowDefinitionLink;
	}

	@Override
	public Object clone() {
		return new StagedGroupedWorkflowDefinitionLinkImpl(
			_workflowDefinitionLink);
	}

	@Override
	public String getClassName() {
		ClassName className = ClassNameLocalServiceUtil.fetchClassName(
			_workflowDefinitionLink.getClassNameId());

		if (className != null) {
			return className.getClassName();
		}

		return null;
	}

	@Override
	public long getClassNameId() {
		return _workflowDefinitionLink.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _workflowDefinitionLink.getClassPK();
	}

	@Override
	public long getCompanyId() {
		return _workflowDefinitionLink.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _workflowDefinitionLink.getCreateDate();
	}

	@Override
	public long getGroupId() {
		return _workflowDefinitionLink.getGroupId();
	}

	@Override
	public Date getLastPublishDate() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _workflowDefinitionLink.getModifiedDate();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _workflowDefinitionLink.getWorkflowDefinitionLinkId();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedGroupedWorkflowDefinitionLink.class);
	}

	@Override
	public long getTypePK() {
		return _workflowDefinitionLink.getTypePK();
	}

	@Override
	public long getUserId() {
		return _workflowDefinitionLink.getUserId();
	}

	@Override
	public String getUserName() {
		return _workflowDefinitionLink.getUserName();
	}

	@Override
	public String getUuid() {
		return String.valueOf(
			_workflowDefinitionLink.getWorkflowDefinitionLinkId());
	}

	public WorkflowDefinitionLink getWorkflowDefinitionLink() {
		return _workflowDefinitionLink;
	}

	@Override
	public long getWorkflowDefinitionLinkId() {
		return _workflowDefinitionLink.getWorkflowDefinitionLinkId();
	}

	@Override
	public String getWorkflowDefinitionName() {
		return _workflowDefinitionLink.getWorkflowDefinitionName();
	}

	@Override
	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionLink.getWorkflowDefinitionVersion();
	}

	@Override
	public void setLastPublishDate(Date date) {
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	private final WorkflowDefinitionLink _workflowDefinitionLink;

}