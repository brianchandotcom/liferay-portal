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

package com.liferay.lcs.rest;

/**
 * @author Ivica Cardic
 */
public class LCSRoleImpl {

	public long getLcsClusterEntryId() {
		return _lcsClusterEntryId;
	}

	public long getLcsProjectId() {
		return _lcsProjectId;
	}

	public long getLcsRoleId() {
		return _lcsRoleId;
	}

	public int getRole() {
		return _role;
	}

	public long getUserId() {
		return _userId;
	}

	public void setLcsClusterEntryId(long lcsClusterEntryId) {
		_lcsClusterEntryId = lcsClusterEntryId;
	}

	public void setLcsProjectId(long lcsProjectId) {
		_lcsProjectId = lcsProjectId;
	}

	public void setLcsRoleId(long lcsRoleId) {
		_lcsRoleId = lcsRoleId;
	}

	public void setRole(int role) {
		_role = role;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _lcsClusterEntryId;
	private long _lcsProjectId;
	private long _lcsRoleId;
	private int _role;
	private long _userId;

}