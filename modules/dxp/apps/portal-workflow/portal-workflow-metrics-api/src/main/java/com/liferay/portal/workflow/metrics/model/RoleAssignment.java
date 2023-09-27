/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class RoleAssignment implements Assignment {

	public RoleAssignment(long assignmentId, List<Long> groupIds) {
		_assignmentId = assignmentId;
		_groupIds = groupIds;
	}

	@Override
	public long getAssignmentId() {
		return _assignmentId;
	}

	public List<Long> getGroupIds() {
		return _groupIds;
	}

	public void setAssignmentId(long assignmentId) {
		_assignmentId = assignmentId;
	}

	public void setGroupId(List<Long> groupIds) {
		_groupIds = groupIds;
	}

	private long _assignmentId;
	private List<Long> _groupIds;

}