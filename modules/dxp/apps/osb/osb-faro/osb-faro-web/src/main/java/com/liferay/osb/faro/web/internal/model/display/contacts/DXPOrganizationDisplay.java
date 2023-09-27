/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.DXPOrganization;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPOrganizationDisplay {

	public DXPOrganizationDisplay() {
	}

	public DXPOrganizationDisplay(DXPOrganization dxpOrganization) {
		_id = GetterUtil.getString(
			dxpOrganization.getId(),
			String.valueOf(dxpOrganization.getOrganizationId()));
		_name = dxpOrganization.getName();
		_organizationId = dxpOrganization.getOrganizationId();
		_parentName = dxpOrganization.getParentName();
		_type = dxpOrganization.getType();
		_usersCount = dxpOrganization.getUsersCount();
	}

	private String _id;
	private String _name;
	private long _organizationId;
	private String _parentName;
	private String _type;
	private long _usersCount;

}