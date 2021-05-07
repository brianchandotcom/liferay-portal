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

import java.util.Collections;
import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLAInstanceResult {

	public long getCompanyId() {
		return _companyId;
	}

	public LocalDateTime getCompletionLocalDateTime() {
		return _completionLocalDateTime;
	}

	public long getElapsedTime() {
		return _elapsedTime;
	}

	public long getInstanceId() {
		return _instanceId;
	}

	public LocalDateTime getModifiedLocalDateTime() {
		return _modifiedLocalDateTime;
	}

	public LocalDateTime getOverdueLocalDateTime() {
		return _overdueLocalDateTime;
	}

	public long getProcessId() {
		return _processId;
	}

	public long getRemainingTime() {
		return _remainingTime;
	}

	public long getSLADefinitionId() {
		return _slaDefinitionId;
	}

	public WorkflowMetricsSLAStatus getWorkflowMetricsSLAStatus() {
		return _workflowMetricsSLAStatus;
	}

	public List<WorkflowMetricsSLATaskResult>
		getWorkflowMetricsSLATaskResults() {

		return _workflowMetricsSLATaskResults;
	}

	public boolean isOnTime() {
		return _onTime;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCompletionLocalDateTime(
		LocalDateTime completionLocalDateTime) {

		_completionLocalDateTime = completionLocalDateTime;
	}

	public void setElapsedTime(long elapsedTime) {
		_elapsedTime = elapsedTime;
	}

	public void setInstanceId(long instanceId) {
		_instanceId = instanceId;
	}

	public void setModifiedLocalDateTime(LocalDateTime modifiedLocalDateTime) {
		_modifiedLocalDateTime = modifiedLocalDateTime;
	}

	public void setOnTime(boolean onTime) {
		_onTime = onTime;
	}

	public void setOverdueLocalDateTime(LocalDateTime overdueLocalDateTime) {
		_overdueLocalDateTime = overdueLocalDateTime;
	}

	public void setProcessId(long processId) {
		_processId = processId;
	}

	public void setRemainingTime(long remainingTime) {
		_remainingTime = remainingTime;
	}

	public void setSLADefinitionId(long slaDefinitionId) {
		_slaDefinitionId = slaDefinitionId;
	}

	public void setWorkflowMetricsSLAStatus(
		WorkflowMetricsSLAStatus workflowMetricsSLAStatus) {

		_workflowMetricsSLAStatus = workflowMetricsSLAStatus;
	}

	public void setWorkflowMetricsSLATaskResults(
		List<WorkflowMetricsSLATaskResult> workflowMetricsSLATaskResults) {

		_workflowMetricsSLATaskResults = workflowMetricsSLATaskResults;
	}

	private long _companyId;
	private LocalDateTime _completionLocalDateTime;
	private long _elapsedTime;
	private long _instanceId;
	private LocalDateTime _modifiedLocalDateTime;
	private boolean _onTime;
	private LocalDateTime _overdueLocalDateTime;
	private long _processId;
	private long _remainingTime;
	private long _slaDefinitionId;
	private WorkflowMetricsSLAStatus _workflowMetricsSLAStatus;
	private List<WorkflowMetricsSLATaskResult> _workflowMetricsSLATaskResults =
		Collections.emptyList();

}