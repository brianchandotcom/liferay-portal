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

package com.liferay.deprecated.modules.upgrade.internal;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Adolfo Pérez
 */
public class UpgradeWYSIWYG extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		LayoutTypeSettingsUtil.removePortletId(
			connection, "com_liferay_wysiwyg_web_portlet_WYSIWYGPortlet");

		runSQL(
			"delete from Portlet where portletId = " +
				"'com_liferay_wysiwyg_web_portlet_WYSIWYGPortlet'");

		runSQL(
			"delete from PortletPreferences where portletId =" +
				"'com_liferay_wysiwyg_web_portlet_WYSIWYGPortlet'");

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.wysiwyg.web'");
	}

}