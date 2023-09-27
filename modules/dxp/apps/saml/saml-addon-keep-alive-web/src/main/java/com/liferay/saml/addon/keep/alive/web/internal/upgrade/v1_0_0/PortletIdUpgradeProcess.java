/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.addon.keep.alive.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Tomas Polesovsky
 */
public class PortletIdUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_deletePortletId();
		_deletePortletPreferences();
		_deleteResourceAction();
		_deleteResourcePermission();
	}

	private void _deletePortletId() throws Exception {
		runSQL(
			"delete from Portlet where portletId like '%1_WAR_samlportlet%'");
	}

	private void _deletePortletPreferences() throws Exception {
		runSQL(
			"delete from PortletPreferences where portletId like " +
				"'%1_WAR_samlportlet%'");
	}

	private void _deleteResourceAction() throws Exception {
		runSQL(
			"delete from ResourceAction where name like '%1_WAR_samlportlet%'");
	}

	private void _deleteResourcePermission() throws Exception {
		runSQL(
			"delete from ResourcePermission where name like " +
				"'%1_WAR_samlportlet%'");
	}

}