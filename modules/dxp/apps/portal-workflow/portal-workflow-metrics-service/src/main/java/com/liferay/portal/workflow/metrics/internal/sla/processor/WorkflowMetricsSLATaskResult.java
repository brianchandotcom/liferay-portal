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

package com.liferay.portal.workflow.metrics.internal.sla.processor;

import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.time.LocalDateTime;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLATaskResult {

	public Long[] getAssigneeIds() {
		return _assigneeIds;
	}

	public String getAssigneeType() {
		return _assigneeType;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public LocalDateTime getCompletionLocalDateTime() {
		return _completionLocalDateTime;
	}

	public Long getCompletionUserId() {
		return _completionUserId;
	}

	public LocalDateTime getInstanceCompletionLocalDateTime() {
		return _instanceCompletionLocalDateTime;
	}

	public long getInstanceId() {
		return _instanceId;
	}

	public LocalDateTime getModifiedLocalDateTime() {
		return _modifiedLocalDateTime;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public long getProcessId() {
		return _processId;
	}

	public long getSLADefinitionId() {
		return _slaDefinitionId;
	}

	public long getTaskId() {
		return _taskId;
	}

	public String getTaskName() {
		return _taskName;
	}

	public WorkflowMetricsSLAStatus getWorkflowMetricsSLAStatus() {
		return _workflowMetricsSLAStatus;
	}

	public boolean isBreached() {
		return _breached;
	}

	public boolean isInstanceCompleted() {
		return _instanceCompleted;
	}

	public boolean isOnTime() {
		return _onTime;
	}

	public void setAssigneeIds(Long[] assigneeIds) {
		_assigneeIds = assigneeIds;
	}

	public void setAssigneeType(String assigneeType) {
		_assigneeType = assigneeType;
	}

	public void setBreached(boolean breached) {
		_breached = breached;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCompletionLocalDateTime(
		LocalDateTime completionLocalDateTime) {

		_completionLocalDateTime = completionLocalDateTime;
	}

	public void setCompletionUserId(Long completionUserId) {
		_completionUserId = completionUserId;
	}

	public void setInstanceCompleted(boolean instanceCompleted) {
		_instanceCompleted = instanceCompleted;
	}

	public void setInstanceCompletionLocalDateTime(
		LocalDateTime instanceCompletionLocalDateTime) {

		_instanceCompletionLocalDateTime = instanceCompletionLocalDateTime;
	}

	public void setInstanceId(long instanceId) {
		_instanceId = instanceId;
	}

	public void setModifiedLocalDateTime(LocalDateTime modifiedLocalDateTime) {
		_modifiedLocalDateTime = modifiedLocalDateTime;
	}

	public void setNodeId(long nodeId) {
		_nodeId = nodeId;
	}

	public void setOnTime(boolean onTime) {
		_onTime = onTime;
	}

	public void setProcessId(long processId) {
		_processId = processId;
	}

	public void setSLADefinitionId(long slaDefinitionId) {
		_slaDefinitionId = slaDefinitionId;
	}

	public void setTaskId(long taskId) {
		_taskId = taskId;
	}

	public void setTaskName(String taskName) {
		_taskName = taskName;
	}

	public void setWorkflowMetricsSLAStatus(
		WorkflowMetricsSLAStatus workflowMetricsSLAStatus) {

		_workflowMetricsSLAStatus = workflowMetricsSLAStatus;
	}

	private Long[] _assigneeIds;
	private String _assigneeType;
	private boolean _breached;
	private long _companyId;
	private LocalDateTime _completionLocalDateTime;
	private Long _completionUserId;
	private boolean _instanceCompleted;
	private LocalDateTime _instanceCompletionLocalDateTime;
	private long _instanceId;
	private LocalDateTime _modifiedLocalDateTime;
	private long _nodeId;
	private boolean _onTime;
	private long _processId;
	private long _slaDefinitionId;
	private long _taskId;
	private String _taskName;
	private WorkflowMetricsSLAStatus _workflowMetricsSLAStatus;

}