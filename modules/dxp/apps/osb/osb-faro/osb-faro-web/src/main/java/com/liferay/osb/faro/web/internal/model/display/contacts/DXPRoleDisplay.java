/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.DXPRole;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPRoleDisplay {

	public DXPRoleDisplay() {
	}

	public DXPRoleDisplay(DXPRole dxpRole) {
		_roleId = dxpRole.getRoleId();
		_id = dxpRole.getId();
		_name = dxpRole.getName();
	}

	private String _id;
	private String _name;
	private long _roleId;

}