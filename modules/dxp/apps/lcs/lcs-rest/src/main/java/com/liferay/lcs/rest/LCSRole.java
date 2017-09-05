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
public interface LCSRole {

	public long getLcsClusterEntryId();

	public long getLcsProjectId();

	public long getLcsRoleId();

	public int getRole();

	public long getUserId();

	public void setLcsClusterEntryId(long lcsClusterEntryId);

	public void setLcsProjectId(long lcsProjectId);

	public void setLcsRoleId(long lcsRoleId);

	public void setRole(int role);

	public void setUserId(long userId);

}