/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.Date;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DefaultWorkflowLog implements Serializable, WorkflowLog {

	@Override
	public long getAuditUserId() {
		return _auditUserId;
	}

	@Override
	public String getComment() {
		return _comment;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public long getPreviousRoleId() {
		return _previousRoleId;
	}

	@Override
	public String getPreviousState() {
		return _previousState;
	}

	@Override
	public long getPreviousUserId() {
		return _previousUserId;
	}

	@Override
	public long getRoleId() {
		return _roleId;
	}

	@Override
	public String getState() {
		return _state;
	}

	@Override
	public int getType() {
		return _type;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public long getWorkflowLogId() {
		return _workflowLogId;
	}

	@Override
	public long getWorkflowTaskId() {
		return _workflowTaskId;
	}

	public void setAuditUserId(long auditUserId) {
		_auditUserId = auditUserId;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setPreviousRoleId(long previousRoleId) {
		_previousRoleId = previousRoleId;
	}

	public void setPreviousState(String previousState) {
		_previousState = previousState;
	}

	public void setPreviousUserId(long previousUserId) {
		_previousUserId = previousUserId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setType(int type) {
		_type = type;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setWorkflowLogId(long workflowLogId) {
		_workflowLogId = workflowLogId;
	}

	public void setWorkflowTaskId(long workflowTaskId) {
		_workflowTaskId = workflowTaskId;
	}

	private long _auditUserId;
	private String _comment;
	private Date _createDate;
	private long _previousRoleId;
	private String _previousState;
	private long _previousUserId;
	private long _roleId;
	private String _state;
	private int _type;
	private long _userId;
	private long _workflowLogId;
	private long _workflowTaskId;

}