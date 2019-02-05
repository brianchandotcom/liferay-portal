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

package com.liferay.user.associated.data.web.internal.display;

/**
 * @author Drew Brokke
 */
public class UserAssociatedEntity {

	public long getContainerId() {
		return _containerId;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUuid() {
		return _name + "_" + _id;
	}

	public void setContainerId(long containerId) {
		_containerId = containerId;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _containerId;
	private long _id;
	private String _name;
	private long _userId;

}