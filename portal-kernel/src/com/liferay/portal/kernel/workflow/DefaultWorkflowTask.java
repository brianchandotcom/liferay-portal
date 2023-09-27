/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.model.User;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DefaultWorkflowTask
	extends BaseWorkflowNode implements Serializable, WorkflowTask {

	@Override
	public long getAssigneeUserId() {
		if (!isAssignedToSingleUser()) {
			return -1;
		}

		WorkflowTaskAssignee workflowTaskAssignee = _workflowTaskAssignees.get(
			0);

		return workflowTaskAssignee.getAssigneeClassPK();
	}

	@Override
	public Date getCompletionDate() {
		return _completionDate;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public Date getDueDate() {
		return _dueDate;
	}

	@Override
	public Map<String, Serializable> getOptionalAttributes() {
		return _optionalAttributes;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public long getWorkflowDefinitionId() {
		return _workflowDefinitionId;
	}

	@Override
	public String getWorkflowDefinitionName() {
		return _workflowDefinitionName;
	}

	@Override
	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionVersion;
	}

	@Override
	public long getWorkflowInstanceId() {
		return _workflowInstanceId;
	}

	@Override
	public List<WorkflowTaskAssignee> getWorkflowTaskAssignees() {
		if (_workflowTaskAssignees == null) {
			return Collections.emptyList();
		}

		return _workflowTaskAssignees;
	}

	@Override
	public long getWorkflowTaskId() {
		return _workflowTaskId;
	}

	@Override
	public boolean isAssignedToSingleUser() {
		if ((_workflowTaskAssignees == null) ||
			(_workflowTaskAssignees.size() != 1)) {

			return false;
		}

		WorkflowTaskAssignee workflowTaskAssignee = _workflowTaskAssignees.get(
			0);

		String userClassName = User.class.getName();

		if (userClassName.equals(workflowTaskAssignee.getAssigneeClassName())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAsynchronous() {
		return _asynchronous;
	}

	@Override
	public boolean isCompleted() {
		if (_completionDate != null) {
			return true;
		}

		return false;
	}

	public void setAsynchronous(boolean asynchronous) {
		_asynchronous = asynchronous;
	}

	public void setCompletionDate(Date completionDate) {
		_completionDate = completionDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDueDate(Date dueDate) {
		_dueDate = dueDate;
	}

	public void setOptionalAttributes(
		Map<String, Serializable> optionalAttributes) {

		_optionalAttributes = optionalAttributes;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setWorkflowDefinitionId(long workflowDefinitionId) {
		_workflowDefinitionId = workflowDefinitionId;
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_workflowDefinitionName = workflowDefinitionName;
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowDefinitionVersion = workflowDefinitionVersion;
	}

	public void setWorkflowInstanceId(long workflowInstanceId) {
		_workflowInstanceId = workflowInstanceId;
	}

	public void setWorkflowTaskAssignees(
		Collection<WorkflowTaskAssignee> workflowTaskAssignees) {

		if (_workflowTaskAssignees != null) {
			_workflowTaskAssignees.addAll(workflowTaskAssignees);

			return;
		}

		if (workflowTaskAssignees instanceof List) {
			_workflowTaskAssignees =
				(List<WorkflowTaskAssignee>)workflowTaskAssignees;
		}
		else {
			_workflowTaskAssignees = new ArrayList<>(workflowTaskAssignees);
		}
	}

	public void setWorkflowTaskId(long workflowTaskId) {
		_workflowTaskId = workflowTaskId;
	}

	private boolean _asynchronous;
	private Date _completionDate;
	private Date _createDate;
	private String _description;
	private Date _dueDate;
	private Map<String, Serializable> _optionalAttributes;
	private String _userName;
	private long _workflowDefinitionId;
	private String _workflowDefinitionName;
	private int _workflowDefinitionVersion;
	private long _workflowInstanceId;
	private List<WorkflowTaskAssignee> _workflowTaskAssignees;
	private long _workflowTaskId;

}