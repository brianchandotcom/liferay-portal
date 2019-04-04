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

import com.liferay.portal.workflow.metrics.internal.sla.WorkfowMetricsSLAStatus;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessor.TaskInterval;

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

	public void complete(LocalDateTime endLocalDateTime) {
		if (isCompleted()) {
			throw new IllegalStateException("Stopwatch is already completed");
		}

		if (!isEmpty()) {
			TaskInterval taskInterval = _taskIntervals.peek();

			taskInterval.setEndLocalDateTime(endLocalDateTime);
		}

		_workfowMetricsSLAStatus = WorkfowMetricsSLAStatus.COMPLETED;
	}

	public List<TaskInterval> getTaskIntervals() {
		return _taskIntervals;
	}

	public WorkfowMetricsSLAStatus getWorkfowMetricsSLAStatus() {
		return _workfowMetricsSLAStatus;
	}

	public boolean isCompleted() {
		if (_workfowMetricsSLAStatus == WorkfowMetricsSLAStatus.COMPLETED) {
			return true;
		}

		return false;
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

	public void pause(LocalDateTime endLocalDateTime) {
		if (isCompleted()) {
			throw new IllegalStateException("Stopwatch is completed");
		}

		if (!isEmpty()) {
			TaskInterval taskInterval = _taskIntervals.peek();

			taskInterval.setEndLocalDateTime(endLocalDateTime);
		}

		_workfowMetricsSLAStatus = WorkfowMetricsSLAStatus.PAUSED;
	}

	public void run(LocalDateTime startLocalDateTime) {
		if (isCompleted()) {
			throw new IllegalStateException("Stopwatch is completed");
		}

		if (isRunning() && !isEmpty()) {
			return;
		}

		TaskInterval taskInterval = new TaskInterval();

		taskInterval.setEndLocalDateTime(LocalDateTime.MAX);
		taskInterval.setStartLocalDateTime(startLocalDateTime);

		_taskIntervals.push(taskInterval);

		_workfowMetricsSLAStatus = WorkfowMetricsSLAStatus.RUNNING;
	}

	private final Stack<TaskInterval> _taskIntervals = new Stack<>();
	private WorkfowMetricsSLAStatus _workfowMetricsSLAStatus;

}