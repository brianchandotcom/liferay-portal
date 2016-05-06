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

package com.liferay.product.navigation.product.menu.web.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Jürgen Kappler
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_deleteControlPanelMenuPreferences();
		_deleteDockbarPreferences();
	}

	private void _deleteControlPanelMenuPreferences() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete control panel menu preferences");
		}

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from PortletPreferences where portletId = " +
				_CONTROL_PANEL_MENU_PORTLET);
	}

	private void _deleteDockbarPreferences() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete dockbar preferences");
		}

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from PortletPreferences where portletId = " +
				_DOCKBAR_PORTLET);
	}

	private static final String _CONTROL_PANEL_MENU_PORTLET = "160";

	private static final String _DOCKBAR_PORTLET = "145";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

}