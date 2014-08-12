/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class WorkflowTaskAssignee implements Serializable {

	public WorkflowTaskAssignee(
		String assigneeClassName, long assigneeClassPK) {

		_assigneeClassName = assigneeClassName;
		_assigneeClassPK = assigneeClassPK;
	}

	public String getAssigneeClassName() {
		return _assigneeClassName;
	}

	public long getAssigneeClassPK() {
		return _assigneeClassPK;
	}

	private final String _assigneeClassName;
	private final long _assigneeClassPK;

}