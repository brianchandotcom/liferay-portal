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

package com.liferay.portal.vulcan.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 */
@XmlRootElement(name = "Permissions")
public class Permissions {

	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	public String[] getActionIds() {
		return _actionIds;
	}

	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	public String getRoleName() {
		return _roleName;
	}

	public void setActionIds(String[] actionIds) {
		_actionIds = actionIds;
	}

	public void setRoleName(String roleName) {
		_roleName = roleName;
	}

	private String[] _actionIds;
	private String _roleName;

}