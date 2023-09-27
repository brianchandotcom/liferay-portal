/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

/**
 * @author Feliphe Marinho
 */
public class UserAssignment implements Assignment {

	public UserAssignment(long assignmentId, String name) {
		_assignmentId = assignmentId;
		_name = name;
	}

	@Override
	public long getAssignmentId() {
		return _assignmentId;
	}

	public String getName() {
		return _name;
	}

	public void setAssignmentId(long assignmentId) {
		_assignmentId = assignmentId;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _assignmentId;
	private String _name;

}