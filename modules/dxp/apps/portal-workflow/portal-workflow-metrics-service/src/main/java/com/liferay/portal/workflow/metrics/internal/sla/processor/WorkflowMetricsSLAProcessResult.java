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

import java.time.LocalDateTime;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLAProcessResult {

	public WorkflowMetricsSLAProcessResult(
		long companyId, long instanceId, long processId, long slaDefinitionId,
		long elapsedTime, LocalDateTime lastCheckLocalDateTime, boolean onTime,
		LocalDateTime overdueLocalDateTime, long remainingTime) {

		_companyId = companyId;
		_instanceId = instanceId;
		_processId = processId;
		_slaDefinitionId = slaDefinitionId;
		_elapsedTime = elapsedTime;
		_lastCheckLocalDateTime = lastCheckLocalDateTime;
		_onTime = onTime;
		_overdueLocalDateTime = overdueLocalDateTime;
		_remainingTime = remainingTime;
	}

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

	public boolean isOnTime() {
		return _onTime;
	}

	private final long _companyId;
	private final long _elapsedTime;
	private final long _instanceId;
	private final LocalDateTime _lastCheckLocalDateTime;
	private final boolean _onTime;
	private final LocalDateTime _overdueLocalDateTime;
	private final long _processId;
	private final long _remainingTime;
	private final long _slaDefinitionId;

}