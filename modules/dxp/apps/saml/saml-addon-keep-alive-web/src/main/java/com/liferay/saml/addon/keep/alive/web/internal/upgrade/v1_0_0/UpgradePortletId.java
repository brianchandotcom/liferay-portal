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

package com.liferay.saml.addon.keep.alive.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Tomas Polesovsky
 */
public class UpgradePortletId extends UpgradeProcess {

	protected void deletePortletId() throws Exception {
		runSQL(
			"delete from Portlet where portletId like '%1_WAR_samlportlet%'");
	}

	protected void deletePortletPreferences() throws Exception {
		runSQL(
			"delete from PortletPreferences where portletId like " +
				"'%1_WAR_samlportlet%'");
	}

	protected void deleteResourceAction() throws Exception {
		runSQL(
			"delete from ResourceAction where name like '%1_WAR_samlportlet%'");
	}

	protected void deleteResourcePermission() throws Exception {
		runSQL(
			"delete from ResourcePermission where name like " +
				"'%1_WAR_samlportlet%'");
	}

	@Override
	protected void doUpgrade() throws Exception {
		deletePortletId();
		deletePortletPreferences();
		deleteResourceAction();
		deleteResourcePermission();
	}

}