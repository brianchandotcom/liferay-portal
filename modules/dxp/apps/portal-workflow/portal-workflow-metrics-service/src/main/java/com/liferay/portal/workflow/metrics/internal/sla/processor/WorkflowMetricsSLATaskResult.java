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

import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.time.LocalDateTime;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLATaskResult {

	public Long getAssigneeId() {
		return _assigneeId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getInstanceId() {
		return _instanceId;
	}

	public LocalDateTime getLastCheckLocalDateTime() {
		return _lastCheckLocalDateTime;
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

	public long getTokenId() {
		return _tokenId;
	}

	public WorkfowMetricsSLAStatus getWorkfowMetricsSLAStatus() {
		return _workfowMetricsSLAStatus;
	}

	public boolean isBreached() {
		return _breached;
	}

	public boolean isOnTime() {
		return _onTime;
	}

	public void setAssigneeId(Long assigneeId) {
		_assigneeId = assigneeId;
	}

	public void setBreached(boolean breached) {
		_breached = breached;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setInstanceId(long instanceId) {
		_instanceId = instanceId;
	}

	public void setLastCheckLocalDateTime(
		LocalDateTime lastCheckLocalDateTime) {

		_lastCheckLocalDateTime = lastCheckLocalDateTime;
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

	public void setTokenId(long tokenId) {
		_tokenId = tokenId;
	}

	public void setWorkfowMetricsSLAStatus(
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus) {

		_workfowMetricsSLAStatus = workfowMetricsSLAStatus;
	}

	private Long _assigneeId;
	private boolean _breached;
	private long _companyId;
	private long _instanceId;
	private LocalDateTime _lastCheckLocalDateTime;
	private boolean _onTime;
	private long _processId;
	private long _slaDefinitionId;
	private long _taskId;
	private String _taskName;
	private long _tokenId;
	private WorkfowMetricsSLAStatus _workfowMetricsSLAStatus;

}