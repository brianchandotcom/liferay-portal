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
public class WorkflowMetricsSLAProcessResult {

	public long getCompanyId() {
		return _companyId;
	}

	public long getElapsedTime() {
		return _elapsedTime;
	}

	public long getInstanceId() {
		return _instanceId;
	}

	public LocalDateTime getLastCheckLocalDateTime() {
		return _lastCheckLocalDateTime;
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

	public WorkfowMetricsSLAStatus getWorkfowMetricsSLAStatus() {
		return _workfowMetricsSLAStatus;
	}

	public boolean isOnTime() {
		return _onTime;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setElapsedTime(long elapsedTime) {
		_elapsedTime = elapsedTime;
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

	public void setWorkfowMetricsSLAStatus(
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus) {

		_workfowMetricsSLAStatus = workfowMetricsSLAStatus;
	}

	private long _companyId;
	private long _elapsedTime;
	private long _instanceId;
	private LocalDateTime _lastCheckLocalDateTime;
	private boolean _onTime;
	private LocalDateTime _overdueLocalDateTime;
	private long _processId;
	private long _remainingTime;
	private long _slaDefinitionId;
	private WorkfowMetricsSLAStatus _workfowMetricsSLAStatus;

}