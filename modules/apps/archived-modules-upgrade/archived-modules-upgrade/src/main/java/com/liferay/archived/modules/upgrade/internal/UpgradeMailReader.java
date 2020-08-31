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
public class UpgradeMailReader extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.mail.reader.model.Account'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.mail.reader.model.Attachment'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.mail.reader.model.Folder'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.mail.reader.model.Message'");

		LayoutTypeSettingsUtil.removePortletId(
			connection, "com_liferay_mail_reader_web_portlet_MailPortlet");

		runSQL(
			"delete from Portlet where portletId = " +
				"'com_liferay_mail_reader_web_portlet_MailPortlet'");

		runSQL(
			"delete from PortletPreferences where portletId = " +
				"'com_liferay_mail_reader_web_portlet_MailPortlet'");

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.mail.reader.service'");
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.mail.reader.web'");

		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.mail.reader.model.Account'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.mail.reader.model.Attachment'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.mail.reader.model.Folder'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.mail.reader.model.Message'");

		runSQL("delete from ServiceComponent where buildNamespace = 'Mail'");

		runSQL("drop table Mail_Account");
		runSQL("drop table Mail_Attachment");
		runSQL("drop table Mail_Folder");
		runSQL("drop table Mail_Message");
	}

}