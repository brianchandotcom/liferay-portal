/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.DXPUser;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPUserDisplay {

	public DXPUserDisplay() {
	}

	public DXPUserDisplay(DXPUser dxpUser) {
		_id = dxpUser.getId();
		_name = dxpUser.getName();
		_userId = dxpUser.getUserId();
	}

	private String _id;
	private String _name;
	private long _userId;

}