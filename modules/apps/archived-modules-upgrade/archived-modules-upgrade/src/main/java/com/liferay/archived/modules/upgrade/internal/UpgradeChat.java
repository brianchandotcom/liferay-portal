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

package com.liferay.archived.modules.upgrade.internal;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Preston Crary
 */
public class UpgradeChat extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.chat.model.Entry'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.chat.model.Status'");

		runSQL(
			"delete from Portlet where portletId = " +
				"'com_liferay_chat_web_portlet_ChatPortlet'");

		runSQL(
			"delete from PortletPreferences where portletId =" +
				"'com_liferay_chat_web_portlet_ChatPortlet'");

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.chat.service'");
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.chat.web'");

		runSQL("delete from ServiceComponent where buildNamespace = 'Chat'");

		runSQL("drop table Chat_Entry");
		runSQL("drop table Chat_Status");
	}

}