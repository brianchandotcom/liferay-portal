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

import java.util.List;
import java.util.Stack;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLAStopwatch {

	public WorkflowMetricsSLAStopwatch(
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus) {

		_workfowMetricsSLAStatus = workfowMetricsSLAStatus;
	}

	public List<WorkflowMetricsSLAProcessor.TaskInterval> getTaskIntervals() {
		return _taskIntervals;
	}

	public WorkfowMetricsSLAStatus getWorkfowMetricsSLAStatus() {
		return _workfowMetricsSLAStatus;
	}

	public boolean isEmpty() {
		return _taskIntervals.isEmpty();
	}

	public boolean isRunning() {
		if (_workfowMetricsSLAStatus == WorkfowMetricsSLAStatus.RUNNING) {
			return true;
		}

		return false;
	}

	public boolean isStopped() {
		if (_workfowMetricsSLAStatus == WorkfowMetricsSLAStatus.STOPPED) {
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

		_workfowMetricsSLAStatus = WorkfowMetricsSLAStatus.PAUSED;
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

		_workfowMetricsSLAStatus = WorkfowMetricsSLAStatus.RUNNING;
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

		_workfowMetricsSLAStatus = WorkfowMetricsSLAStatus.STOPPED;
	}

	private final Stack<WorkflowMetricsSLAProcessor.TaskInterval>
		_taskIntervals = new Stack<>();
	private WorkfowMetricsSLAStatus _workfowMetricsSLAStatus;

}