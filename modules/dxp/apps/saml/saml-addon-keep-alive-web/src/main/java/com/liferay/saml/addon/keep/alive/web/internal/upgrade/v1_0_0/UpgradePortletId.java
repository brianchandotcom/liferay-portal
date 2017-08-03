/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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