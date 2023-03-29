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

import com.liferay.osb.faro.engine.client.model.DXPUserGroup;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPUserGroupDisplay {

	public DXPUserGroupDisplay() {
	}

	public DXPUserGroupDisplay(DXPUserGroup dxpUserGroup) {
		_id = GetterUtil.getString(
			dxpUserGroup.getId(),
			String.valueOf(dxpUserGroup.getUserGroupId()));
		_name = dxpUserGroup.getName();
		_userGroupId = dxpUserGroup.getUserGroupId();
		_usersCount = dxpUserGroup.getUsersCount();
	}

	private String _id;
	private String _name;
	private long _userGroupId;
	private long _usersCount;

}