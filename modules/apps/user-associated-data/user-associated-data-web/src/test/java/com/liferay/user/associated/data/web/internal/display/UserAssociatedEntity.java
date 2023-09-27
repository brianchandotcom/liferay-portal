/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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