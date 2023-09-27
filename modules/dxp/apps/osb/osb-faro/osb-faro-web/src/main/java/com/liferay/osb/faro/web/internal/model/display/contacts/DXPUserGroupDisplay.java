/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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