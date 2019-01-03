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

package com.liferay.portal.workflow.reports.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class WorkflowProcessBag {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowProcessBag)) {
			return false;
		}

		WorkflowProcessBag workflowProcessBag = (WorkflowProcessBag)obj;

		if (Objects.equals(
				_workflowProcesses, workflowProcessBag._workflowProcesses) &&
			Objects.equals(_total, workflowProcessBag._total)) {

			return true;
		}

		return false;
	}

	public long getTotal() {
		return _total;
	}

	public List<WorkflowProcess> getWorkflowProcesses() {
		return _workflowProcesses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_workflowProcesses, _total);
	}

	public void setTotal(long total) {
		_total = total;
	}

	public void setWorkflowProcesses(List<WorkflowProcess> workflowProcesses) {
		_workflowProcesses = workflowProcesses;
	}

	private long _total;
	private List<WorkflowProcess> _workflowProcesses = new ArrayList<>();

}