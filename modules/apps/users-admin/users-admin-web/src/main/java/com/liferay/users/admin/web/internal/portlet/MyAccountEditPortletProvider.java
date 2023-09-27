/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.portlet;

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "model.class.name=" + PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
	service = PortletProvider.class
)
public class MyAccountEditPortletProvider extends BasePortletProvider {

	@Override
	public String getPortletName() {
		return UsersAdminPortletKeys.MY_ACCOUNT;
	}

	@Override
	public Action[] getSupportedActions() {
		return _supportedActions;
	}

	private final Action[] _supportedActions = {
		Action.EDIT, Action.MANAGE, Action.VIEW
	};

}