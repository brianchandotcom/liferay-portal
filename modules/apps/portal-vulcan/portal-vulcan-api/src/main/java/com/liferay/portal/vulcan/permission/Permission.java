/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.permission;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 */
@GraphQLName("Permission")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Permission")
public class Permission {

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	public String[] getActionIds() {
		return actionIds;
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	public String getRoleName() {
		return roleName;
	}

	public void setActionIds(String[] actionIds) {
		this.actionIds = actionIds;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	protected String[] actionIds;
	protected String roleName;

}