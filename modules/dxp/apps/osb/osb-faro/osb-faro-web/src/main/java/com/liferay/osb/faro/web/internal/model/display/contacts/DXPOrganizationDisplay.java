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