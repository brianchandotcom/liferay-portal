/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.sla.processor;

import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Stack;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLAStopwatch {

	public WorkflowMetricsSLAStopwatch(
		WorkflowMetricsSLAStatus workflowMetricsSLAStatus) {

		_workflowMetricsSLAStatus = workflowMetricsSLAStatus;
	}

	public List<WorkflowMetricsSLAProcessor.TaskInterval> getTaskIntervals() {
		return _taskIntervals;
	}

	public WorkflowMetricsSLAStatus getWorkflowMetricsSLAStatus() {
		return _workflowMetricsSLAStatus;
	}

	public boolean isEmpty() {
		return _taskIntervals.isEmpty();
	}

	public boolean isRunning() {
		if (_workflowMetricsSLAStatus == WorkflowMetricsSLAStatus.RUNNING) {
			return true;
		}

		return false;
	}

	public boolean isStopped() {
		if (_workflowMetricsSLAStatus == WorkflowMetricsSLAStatus.STOPPED) {
			return true;
		}

		return false;
	}

	public void pause(LocalDateTime endLocalDateTime) {
		if (isStopped()) {
			throw new IllegalStateException("Stopwatch is stopped");
		}

		if (!isEmpty()) {
			WorkflowMetricsSLAProcessor.TaskInterval taskInterval =
				_taskIntervals.peek();

			taskInterval.setEndLocalDateTime(endLocalDateTime);
		}

		_workflowMetricsSLAStatus = WorkflowMetricsSLAStatus.PAUSED;
	}

	public void run(LocalDateTime startLocalDateTime) {
		if (isStopped()) {
			throw new IllegalStateException("Stopwatch is stopped");
		}

		if (isRunning() && !isEmpty()) {
			return;
		}

		WorkflowMetricsSLAProcessor.TaskInterval taskInterval =
			new WorkflowMetricsSLAProcessor.TaskInterval();

		taskInterval.setEndLocalDateTime(LocalDateTime.MAX);
		taskInterval.setStartLocalDateTime(startLocalDateTime);

		_taskIntervals.push(taskInterval);

		_workflowMetricsSLAStatus = WorkflowMetricsSLAStatus.RUNNING;
	}

	public void stop(LocalDateTime endLocalDateTime) {
		if (isStopped()) {
			throw new IllegalStateException("Stopwatch is already stopped");
		}

		if (!isEmpty()) {
			WorkflowMetricsSLAProcessor.TaskInterval taskInterval =
				_taskIntervals.peek();

			taskInterval.setEndLocalDateTime(endLocalDateTime);
		}

		_workflowMetricsSLAStatus = WorkflowMetricsSLAStatus.STOPPED;
	}

	private final Stack<WorkflowMetricsSLAProcessor.TaskInterval>
		_taskIntervals = new Stack<>();
	private WorkflowMetricsSLAStatus _workflowMetricsSLAStatus;

}