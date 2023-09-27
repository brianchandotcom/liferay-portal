/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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