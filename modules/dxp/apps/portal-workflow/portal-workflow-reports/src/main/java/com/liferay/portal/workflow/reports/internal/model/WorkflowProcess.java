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

import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class WorkflowProcess {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowProcess)) {
			return false;
		}

		WorkflowProcess workflowProcess = (WorkflowProcess)obj;

		if (Objects.equals(_instancesCount, workflowProcess._instancesCount) &&
			Objects.equals(
				_instancesOntime, workflowProcess._instancesOntime) &&
			Objects.equals(
				_instancesOverdue, workflowProcess._instancesOverdue) &&
			Objects.equals(_name, workflowProcess._name) &&
			Objects.equals(_title, workflowProcess._title)) {

			return true;
		}

		return false;
	}

	public long getInstancesCount() {
		return _instancesCount;
	}

	public long getInstancesOntime() {
		return _instancesOntime;
	}

	public long getInstancesOverdue() {
		return _instancesOverdue;
	}

	public String getName() {
		return _name;
	}

	public String getTitle() {
		return _title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_instancesCount, _instancesOntime, _instancesOverdue, _name,
			_title);
	}

	public void setInstancesCount(long instancesCount) {
		_instancesCount = instancesCount;
	}

	public void setInstancesOntime(int instancesOntime) {
		_instancesOntime = instancesOntime;
	}

	public void setInstancesOverdue(int instancesOverdue) {
		_instancesOverdue = instancesOverdue;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private long _instancesCount;
	private long _instancesOntime;
	private long _instancesOverdue;
	private String _name;
	private String _title;

}