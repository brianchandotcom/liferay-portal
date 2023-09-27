/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DefaultWorkflowInstance implements Serializable, WorkflowInstance {

	@Override
	public void addChildWorkflowInstance(
		WorkflowInstance childWorkflowInstance) {

		_childrenWorkflowInstances.add(childWorkflowInstance);
	}

	@Override
	public int getChildrenWorkflowInstanceCount() {
		return _childrenWorkflowInstances.size();
	}

	@Override
	public List<WorkflowInstance> getChildrenWorkflowInstances() {
		return _childrenWorkflowInstances;
	}

	@Override
	public List<WorkflowNode> getCurrentWorkflowNodes() {
		return _currentWorkflowNodes;
	}

	@Override
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public WorkflowInstance getParentWorkflowInstance() {
		return _parentWorkflowInstance;
	}

	@Override
	public long getParentWorkflowInstanceId() {
		if (_parentWorkflowInstance != null) {
			return _parentWorkflowInstance.getWorkflowInstanceId();
		}

		return 0;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public Map<String, Serializable> getWorkflowContext() {
		return _workflowContext;
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
	public boolean isActive() {
		return _active;
	}

	@Override
	public boolean isComplete() {
		if (getEndDate() != null) {
			return true;
		}

		return false;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setChildrenWorkflowInstances(
		List<WorkflowInstance> childrenWorkflowInstances) {

		_childrenWorkflowInstances = childrenWorkflowInstances;
	}

	public void setCurrentWorkflowNodes(
		List<WorkflowNode> currentWorkflowNodes) {

		_currentWorkflowNodes = currentWorkflowNodes;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@Override
	public void setParentWorkflowInstance(
		WorkflowInstance parentWorkflowInstance) {

		_parentWorkflowInstance = parentWorkflowInstance;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setWorkflowContext(Map<String, Serializable> workflowContext) {
		_workflowContext = workflowContext;
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

	private boolean _active;
	private List<WorkflowInstance> _childrenWorkflowInstances =
		new ArrayList<>();
	private List<WorkflowNode> _currentWorkflowNodes;
	private Date _endDate;
	private WorkflowInstance _parentWorkflowInstance;
	private Date _startDate;
	private Map<String, Serializable> _workflowContext;
	private String _workflowDefinitionName;
	private int _workflowDefinitionVersion;
	private long _workflowInstanceId;

}